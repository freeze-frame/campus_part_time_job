package com.edu.boot.entity.Vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SResumeAndNid {
    private Integer nId;
    /**
     *
     */
    private Integer tId;

    /**
     *
     */
    private Integer uid;
    /**
     *
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     *
     */
    private String filename;

    /**
     *
     */
    private String filepath;


    /**
     * 通知信息
     */
    private String notesInfo;

    /**
     * 通知状态
     */
    private Integer state;


    /**
     *
     */
    private Integer recId;

    /**
     *
     */
    private Integer bId;

    /**
     *
     */
    private Integer bossId;


}
