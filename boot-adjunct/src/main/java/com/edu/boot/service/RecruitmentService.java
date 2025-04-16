package com.edu.boot.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.edu.boot.entity.Recruitment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.boot.entity.SResume;
import com.edu.boot.entity.Vo.RecruitmentAllInfoVo;
import com.edu.boot.entity.Vo.RecruitmentAndSonNotesManageVo;
import com.edu.boot.entity.Vo.ShopAndRecruitmentVo;

import java.util.List;

/**
* @author 。
* @description 针对表【recruitment】的数据库操作Service
* @createDate 2023-11-16 13:53:20
*/
public interface RecruitmentService extends IService<Recruitment> {

    /**
     * 通过uid bid获取这个用户的某个商店的所有招聘信息
     * @param uid
     * @param bid
     * @return
     */
    List<RecruitmentAllInfoVo> getRecruitmentAndInfoById(Integer uid,Integer bid);
    List<Recruitment> getRecruitmentById(Integer id);

    //通过商店id获取商店与简历信息
    List<ShopAndRecruitmentVo> getShopAndRecruitmentInfoByBid(Integer bid);
    //通过招聘id获取所有信息
    RecruitmentAllInfoVo getUserAllShopRecruitmentInfoByRid(Integer rid);
    //获取所有招聘
    List<RecruitmentAllInfoVo> getUserAllInfo();
    //搜索获取指定的招聘信息
    List<RecruitmentAllInfoVo> getUserAllInfoSearch(String search);

}
