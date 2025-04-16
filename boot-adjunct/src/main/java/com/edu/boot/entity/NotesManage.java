package com.edu.boot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName notes_manage
 */
@Data
@TableName(value ="notes_manage")
public class NotesManage implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    @JsonProperty("nId")
    private Integer nId;

    /**
     * 
     */
    @JsonProperty("uid")
    private Integer uid;

    /**
     * 通知信息
     */
    @JsonProperty("notesInfo")
    private String notesInfo;

    /**
     * 通知状态
     */
    @JsonProperty("state")

    private Integer state;

    /**
     * 
     */
    @JsonProperty("tId")

    private Integer tId;

    /**
     * 
     */
    @JsonProperty("recId")

    private Integer recId;

    /**
     * 
     */
    @JsonProperty("bId")

    private Integer bId;

    /**
     * 
     */
    @JsonProperty("bossId")

    private Integer bossId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}