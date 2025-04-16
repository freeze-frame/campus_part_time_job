package com.edu.boot.entity.Vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SResumeAndUserVo {
    private Integer uid;
    //用户名
    private String username;

    //头像base64
    private String imageData;

    //手机号
    private String mobile;
    //邮箱
    private String email;
    //年龄
    private Integer age;
    //权限表id pid
    private Integer pid;

    private Integer tId;

    /**
     *
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     *
     */
    private String filename;

    /**
     *
     */
    private String filepath;
}
