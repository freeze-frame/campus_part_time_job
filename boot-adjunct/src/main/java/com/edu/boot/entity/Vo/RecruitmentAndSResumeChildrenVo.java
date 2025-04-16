package com.edu.boot.entity.Vo;

import com.edu.boot.entity.SResume;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class RecruitmentAndSResumeChildrenVo {
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

    private List<SResume> children;
}
