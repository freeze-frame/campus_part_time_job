package com.edu.boot.entity.Vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.edu.boot.entity.Recruitment;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BusinessShopAllInfoVo implements Serializable {
    //用户基准id
    private Integer uid;
    //账号
    private String username;
    //用户名
    private String realname;
    //手机号
    private String mobile;
    //邮箱
    private String email;
    //用户认证
    private String identityImage;
    //年龄
    private Integer age;
    //权限表id pid
    private Integer pid;
    //商店编号
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
    //商店认证状态
    @JsonProperty("bIdentity")
    private int bIdentity;
    //商店认证图
    @JsonProperty("bIdentityImage")
    private String bIdentityImage;
    //存放简历对象和id的
    private List<RecruitmentAndSonNotesManageVo> children;



}
