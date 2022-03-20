package com.miku.springsecuritydemo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.miku.springsecuritydemo.mapper.UserMapper;
import com.miku.springsecuritydemo.po.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;

@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //数据库查询数据
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        Users userPo = userMapper.selectOne(wrapper);
        //判断是否为空
        if(userPo == null){
             throw new UsernameNotFoundException("用户不存在");
        }else{
            List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList("role");
            return new User(userPo.getUsername(),new BCryptPasswordEncoder().encode(userPo.getPassword()),auths);
        }

    }
}
