package com.edu.boot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * 
 * @TableName s_resume
 */
@TableName(value ="s_resume")
@Data
public class SResume implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer tId;

    /**
     * 
     */
    private Integer uid;
    /*
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
    /*
    * */
    private String filetype;

//    private Integer nId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}