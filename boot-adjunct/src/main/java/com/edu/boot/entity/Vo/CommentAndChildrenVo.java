package com.edu.boot.entity.Vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.edu.boot.entity.CommentReply;
import com.edu.boot.entity.Recruitment;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class CommentAndChildrenVo {

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
     * 评论id
     */
    private Integer comId;

    /**
     * 内容
     */
    private String comRemark;

    /**
     * 简历id
     */
    private Integer recId;


    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    private List<CommentReplyAndUserInfoVo> children;

}
