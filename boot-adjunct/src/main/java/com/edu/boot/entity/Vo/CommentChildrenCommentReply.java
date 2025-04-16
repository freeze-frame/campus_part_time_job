package com.edu.boot.entity.Vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.edu.boot.entity.CommentReply;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CommentChildrenCommentReply {


    private Integer comId;
    private Integer uid;
    private String comRemark;

    @TableField(value = "create_time")
    private LocalDateTime createTime;

    public List<CommentReply> children;
}
