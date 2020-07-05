package com.zhiyou100.config;

import com.zhiyou100.service.MyUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

@Configuration//配置类注解
@EnableWebSecurity//开启security服务
@EnableGlobalMethodSecurity(prePostEnabled = true)//开启springsecurity注解使用
public class MySecurityConfig extends WebSecurityConfigurerAdapter{
    //注入自定义认证类
    @Autowired
    private MyUserDetailsServiceImpl userDetailsService;
    //注入异常处理类
    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;
    /**
     * 注入自定义PermissionEvaluator
     */
    @Bean
    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler(){
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setPermissionEvaluator(new MyPermissionEvaluatorImpl());
        return handler;
    }

    //放行静态页面
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**","/js/**","/images/**");
    }

    //定义具体的路径资源对应的权限
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//                .antMatchers("/anon").permitAll()// 允许匿名访问的路径
//                .antMatchers("/user").hasRole("USER")//user路径需要ROLE_USER角色才能访问
//                .antMatchers("/admin").hasRole("ADMIN")//admin路径需要ROLE_ADMIN角色才能访问
                .anyRequest().authenticated()//所有请求都需要认证后才能访问
            .and()
                .formLogin().loginPage("/login")//设置登陆页
                .failureUrl("/login.html")//设置登录失败的跳转页面
                .defaultSuccessUrl("/").permitAll()//设置登陆成功页
                .usernameParameter("username")//自定义登陆用户名参数,默认username
                .passwordParameter("password")//自定义登陆密码参数,默认password
            .and()
                .logout().permitAll()//设置退出登陆允许匿名访问
            .and()
                .exceptionHandling().accessDeniedHandler(myAccessDeniedHandler);
        //关闭csrf跨域
        http.csrf().disable();
    }
    // 密码的转码解码 --> 暂时都是明文存明文取,没有任何加密
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return charSequence.toString();
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return s.equals(charSequence.toString());
            }
        });
    }
}
