package com.miku.springsecuritydemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 用于修改security默认用户名和密码的配置文件
 * @author 10833
 *
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //自己实现这个userDetailsService
    @Autowired
    private UserDetailsService userDetailsService;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        //用于文件加密
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        String password = bCryptPasswordEncoder.encode("miku");
//        auth.inMemoryAuthentication().withUser("miku").password(password).roles("admin");
        auth.userDetailsService(userDetailsService).passwordEncoder(init());
    }

    @Bean
    BCryptPasswordEncoder init(){
        return  new BCryptPasswordEncoder();
    }
}
