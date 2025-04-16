package com.edu.boot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.edu.boot.entity.Users;
import com.edu.boot.mapper.UserMapper;
import com.edu.boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, Users> implements UserService{
    @Autowired
    UserMapper userMapper;
    public IPage<Users> selectUserByGender(Page<Users> page, String str){
        return userMapper.selectUserByGender(page,str);
    }

    @Override
    public IPage<Users> pageByGender(Page<Users> page, QueryWrapper<Users> queryWrapper) {
        return userMapper.pageSelect(page,queryWrapper);
    }

}
