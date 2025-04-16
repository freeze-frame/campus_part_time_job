package com.edu.boot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.bind.Name;

/**
 * 
 * @TableName business_shop
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("business_shop")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BusinessShop implements Serializable {
    /**
     * 商店编号
     */
    @TableId(value = "b_id",type = IdType.AUTO)
    @JsonProperty("bId")
    private Integer bId;

    /**
     * 商家主人id
     */
    private Integer uid;

    /**
     * 商店名
     */
    @JsonProperty("bName")
    private String bName;

    /**
     * 商店图片
     */
    @JsonProperty("bImage")
    private String bImage;

    /**
     * 负责人
     */
    @JsonProperty("bHead")
    private String bHead;
    /**
     * 商店地址
     */
    @JsonProperty("bAddress")
    private String bAddress;

    /*
    * 商店认证图
    */
    @JsonProperty("bIdentityImage")
    private String bIdentityImage;
    /*
    * 商店认证状态
    */
    @JsonProperty("bIdentity")
    private int bIdentity;


}