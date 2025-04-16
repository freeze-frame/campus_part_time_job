package com.edu.boot.entity.Vo;

import com.edu.boot.entity.SResume;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class RecruitmentAndSonNotesManageVo {
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
     * 热度
     */
    private Integer recHeat;
    /**
     * 招聘对象
     */
    private String recObj;

    /**
     * 招聘介绍
     */
    private String recInfo;

    private List<SResumeAndNid> children;

}
