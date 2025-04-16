package com.edu.boot.mapper;

import com.edu.boot.entity.Recruitment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edu.boot.entity.Vo.RecruitmentAllInfoVo;
import com.edu.boot.entity.Vo.ShopAndRecruitmentVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author 。
* @description 针对表【recruitment】的数据库操作Mapper
* @createDate 2023-11-16 13:53:20
* @Entity com.edu.boot.entity.Recruitment
*/
public interface RecruitmentMapper extends BaseMapper<Recruitment> {
    /**
     * 通过uid rid获取所有用户 商店 招聘信息
     * @param uid
     * @param bid
     * @return
     */
    @Select("SELECT * FROM `user` LEFT JOIN `business_shop` ON (${uid}=business_shop.uid) LEFT JOIN `recruitment` ON (${bid}=recruitment.b_id) WHERE user.uid=business_shop.uid AND business_shop.b_id=recruitment.b_id;")
    List<RecruitmentAllInfoVo> getRecruitmentAndInfoById(Integer uid,Integer bid);

    @Select("SELECT * FROM `business_shop` LEFT JOIN `recruitment` ON ${bid}=recruitment.b_id WHERE business_shop.b_id=recruitment.b_id;")
    List<ShopAndRecruitmentVo> getShopAndRecruitmentInfoByBid(Integer bid);

    /**
     * 通过商店bid 获取招聘信息
     * @param id
     * @return
     */
    @Select("select * form user where ${id} = uid")
    List<Recruitment> getRecruitmentById(Integer id);

    @Select("SELECT * FROM `user` LEFT JOIN `business_shop` ON (user.uid=business_shop.uid) LEFT JOIN `recruitment` ON (business_shop.b_id=recruitment.b_id) WHERE user.uid=business_shop.uid AND business_shop.b_id=recruitment.b_id;")
    List<RecruitmentAllInfoVo> getUserAllInfo();
    @Select("SELECT * FROM `user` LEFT JOIN `business_shop` ON (user.uid=business_shop.uid) LEFT JOIN `recruitment` ON (business_shop.b_id=recruitment.b_id) WHERE ${rid}=rec_id AND user.uid=business_shop.uid AND business_shop.b_id=recruitment.b_id;")
    RecruitmentAllInfoVo getUserAllShopRecruitmentInfoByRid(Integer rid);

    @Select("SELECT * FROM `user` LEFT JOIN `business_shop` ON (user.uid=business_shop.uid) LEFT JOIN (SELECT * FROM `recruitment` WHERE recruitment.rec_station LIKE #{search}) AS recruitment ON (business_shop.b_id=recruitment.b_id) WHERE user.uid=business_shop.uid AND business_shop.b_id=recruitment.b_id;")
    List<RecruitmentAllInfoVo> getUserAllInfoSearch(String search);
}




