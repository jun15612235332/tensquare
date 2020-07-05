package com.tensquare.service;

import com.tensquare.model.User;
import com.tensquare.repository.UserRepository;
import com.tensquare.util.IdWorker;
import com.tensquare.util.JwtUtil;
import com.tensquare.util.ResultObject;
import com.tensquare.util.StatusCode;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    UserRepository userRepository;
    @Autowired
    IdWorker idWorker;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    HttpServletRequest request;

    public void sendsms(String mobile) {
        //随机一个验证码
        String random = RandomStringUtils.randomNumeric(6);
        //把这个验证码放入redis缓存中
        redisTemplate.opsForValue().set("code",random,60, TimeUnit.SECONDS);
        //给用户发一份
        Map<String,String> map=new HashMap<>();
        map.put("mobile",mobile);
        map.put("code",random);
        rabbitTemplate.convertAndSend("sms",map);
        System.out.println("验证码为: "+random);

    }

    public String regidter(String code, User user) {
        //判断验证码是否为空
        if (code==null || "".equals(code)){
            return "验证码为空";
        }
        //获取缓存中的验证码
        String s = (String) redisTemplate.opsForValue().get("code");
        if (!code.equals(s)){
            return "验证码错误";
        }
        //添加到数据库
        user.setId(idWorker.nextId()+"");
        //对密码进行加密
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "注册成功";
    }

    public ResultObject login(User user) {
        User user1=userRepository.findByMobile(user.getMobile());
        if (user1 != null && bCryptPasswordEncoder.matches(user.getPassword(),user1.getPassword())){
            //创建token, admin角色应该从数据库查询得到,这里简化了
            String token = jwtUtil.createJWT(user1.getId(), user1.getMobile(), "admin");
            Map map=new HashMap();
            map.put("token",token);
            map.put("user",user);
            return new ResultObject(true, StatusCode.OK,"登录成功",map);
        }
        return new ResultObject(false, StatusCode.LOGINERROR,"登录失败");
    }

    public ResultObject delete(String userId) {
        //获取请求头, Authorization这个名字是与前端商量好的
        String authHeader = request.getHeader("Authorization");
        System.out.println("请求头 : "+authHeader);
        if (authHeader == null){
            return new ResultObject(false, StatusCode.ACCESSERROR, "权限不足");
        }
        Claims claims = jwtUtil.parseJWT(authHeader);
        if (claims == null){
            return new ResultObject(false, StatusCode.ACCESSERROR, "权限不足");
        }
        if (!"admin".equals(claims.get("roles"))){
            return new ResultObject(false, StatusCode.ACCESSERROR, "权限不足");
        }
        userRepository.deleteById(userId);
        return new ResultObject(true, StatusCode.OK, "删除成功");
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
