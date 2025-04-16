package com.edu.boot.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("power")
public class Power {
    //pid权限表id
    private Integer pid;
    //权限表职称
    private String powerName;
}

