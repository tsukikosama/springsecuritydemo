package com.miku.springsecuritydemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.miku.springsecuritydemo.po.Users;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<Users> {
}
