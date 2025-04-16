package com.edu.boot.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.boot.entity.Users;

import java.util.List;


public interface UserService extends IService<Users> {
    IPage<Users> selectUserByGender(Page<Users> page, String str);

    IPage<Users> pageByGender(Page<Users> page, QueryWrapper<Users> queryWrapper);

}
