package com.edu.boot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @TableName banner
 */
@TableName(value ="banner")
@Data
@EqualsAndHashCode(callSuper = false)
public class Banner implements Serializable {
    /**
     * 轮播图编号id
     */
    @TableId(type = IdType.AUTO)
    private Integer rotId;

    /**
     * 招聘表id 外键
     */
    private Integer recId;

    /**
     * 轮播图
     */
    private String rotImage;

    /**
     * 头条标题
     */
    private String topTitle;

    /**
     * 路径
     */
    private String bannerUrl;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}