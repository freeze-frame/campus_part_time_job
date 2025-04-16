package com.edu.boot.entity.Vo;

import lombok.Data;

@Data
public class EditPasswordVo {
    private Integer uid;
    private String oldPassword;
    private String password;

}
