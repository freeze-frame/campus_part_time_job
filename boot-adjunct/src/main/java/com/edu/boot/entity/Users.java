package com.edu.boot.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Users implements Serializable {
    //uid 用户自增id
    @TableId(value = "uid",type = IdType.AUTO)
    private Integer uid;
    //用户名
    private String username;
    //密码
    private String password;
    //头像base64
    private String imageData;
    //用户身份认证图片base64
    private String identityImage;
    //姓名
    private String realname;
    //创建时间
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    //手机号
    private String mobile;
    //邮箱
    private String email;
    //年龄
    private Integer age;
    //权限表id pid
    private Integer pid;
    //身份认证
    private int identification;

    /**
     * 加密盐值
     */
    private String salt;
    public Users(String uname, String passwd) {
        this.username = uname;
        this.password = passwd;
        System.out.println(this.createTime);
    }
}