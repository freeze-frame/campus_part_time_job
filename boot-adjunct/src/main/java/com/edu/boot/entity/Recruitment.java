package com.edu.boot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * @TableName recruitment
 */
@TableName(value = "recruitment")
@Data
public class Recruitment implements Serializable {
    /**
     * 招聘表编号 主键
     */
    @TableId(value = "rec_id",type = IdType.AUTO)
    private Integer recId;

    /**
     * 商店id 外键
     */
    private Integer bId;

    /**
     * 招聘岗位
     */
    private String recStation;

    /**
     * 招聘薪资
     */
    private BigDecimal recSalary;

    /**
     * 热度
     */
    private Integer recHeat;

    /**
     * 招聘介绍
     */
    private String recInfo;
    /**
     * 招聘对象
     */
    private String recObj;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}