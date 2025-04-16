package com.edu.boot.entity.Vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class RecruitmentAndBanner {
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
}
