package com.edu.boot.entity.Vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ShopAndRecruitmentVo {
    //商店编号
    private Integer bId;

    //商店名
    private String bName;
    //商店地址
    private String bAddress;
    //商店图片
    private String bImage;
    //负责人
    private String bHead;

    /**
     * 招聘表编号 主键
     */
    private Integer recId;

    /**
     * 招聘岗位
     */
    private String recStation;

    /**
     * 招聘薪资
     */
    private BigDecimal recSalary;
    /**
     * 招聘介绍
     */
    private String recInfo;
    /**
     * 招聘对象
     */
    private String recObj;
}
