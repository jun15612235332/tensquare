package com.tensquare.service;

import com.tensquare.model.Admin;
import com.tensquare.repository.AdminRepository;
import com.tensquare.util.IdWorker;
import com.tensquare.util.ResultObject;
import com.tensquare.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private IdWorker idWorker;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void add(Admin admin) {
        admin.setId(idWorker.nextId()+"");
        //对密码进行加密
        admin.setPassword(bCryptPasswordEncoder.encode(admin.getPassword()));
        adminRepository.save(admin);
    }

    public ResultObject login(Admin admin) {
        Admin admin1=adminRepository.findByLoginname(admin.getLoginname());
        if (admin1 != null && bCryptPasswordEncoder.matches(admin.getPassword(),admin1.getPassword())){
            return new ResultObject(true,StatusCode.OK,"登录成功");
        }
        return new ResultObject(false, StatusCode.LOGINERROR,"登录失败");
    }
}
