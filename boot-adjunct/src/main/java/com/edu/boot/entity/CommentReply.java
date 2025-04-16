package com.edu.boot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName comment_reply
 */
@TableName(value ="comment_reply")
@Data
public class CommentReply implements Serializable {
    /**
     * 子评论id
     */
    @TableId(type = IdType.AUTO)
    private Integer repId;

    /**
     * 用户id
     */
    private Integer uid;

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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}