package com.edu.boot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName top
 */
@TableName(value ="top")
@Data
public class Top implements Serializable {
    /**
     * top列表编号
     */
    @TableId(type = IdType.AUTO)
    private Integer topId;

    /**
     * 招聘表id
     */
    private Integer recId;

    /**
     * top 标题
     */
    private Integer recTitle;

    /**
     * 前往的路径
     */
    private String topUrl;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}