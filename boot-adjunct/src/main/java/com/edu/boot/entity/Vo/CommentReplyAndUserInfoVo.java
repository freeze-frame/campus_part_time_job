package com.edu.boot.entity.Vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class CommentReplyAndUserInfoVo {

    //用户基准id
    private Integer uid;
    //用户名
    private String username;
    //手机号
    private String mobile;
    //头像
    private String imageData;

    //邮箱
    private String email;
    //年龄
    private Integer age;

    /**
     * 子评论id
     */
    private Integer repId;



    /**
     * 内容
     */
    private String repRemark;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 父级id 外键
     */
    private Integer answerId;


}
