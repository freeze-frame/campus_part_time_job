package com.edu.boot.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.boot.entity.Users;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<Users> {
    //登录sql
    @Select("select * from user where username=#{username} and password=#{password}")
    Users getUserByNameAndPassword(String username,String password);

    //注册sql
    @Insert("INSERT INTO `user`(`username`,`password`,`pid`,`mobile`,`email`,`age`,`realname`,`identityimage`) VALUES(#{username},#{password},#{pid},#{mobile},#{email},#{age},#{realname},#{identity_image})")
    int insertUser(Users users);

    //注册判断用户是否已存在sql
    @Select("select * from user where username=#{username}")
    Integer judgeUser(String username);

    //
    IPage<Users> selectUserByGender(Page<Users> page, String str);

    //
    IPage<Users> pageSelect(Page<Users> page, QueryWrapper<Users> queryWrapper);
    @Select(" SELECT identification FROM user WHERE uid = #{uid}")
    Integer ifIdentity(Integer uid);
}
