package com.edu.boot.entity.Vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RecruitmentAllInfoVo {
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
    //权限表id pid
    private Integer pid;

    //商店编号
    @JsonProperty("bId")
    private Integer bId;
    //商店名
    @JsonProperty("bName")
    private String bName;
    //商店地址
    @JsonProperty("bAddress")
    private String bAddress;
    //商店图片
    @JsonProperty("bImage")
    private String bImage;
    //负责人
    @JsonProperty("bHead")
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
     * 热度
     */
    private Integer recHeat;
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
