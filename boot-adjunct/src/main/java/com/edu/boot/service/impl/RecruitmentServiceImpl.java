package com.edu.boot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.boot.entity.Recruitment;
import com.edu.boot.entity.Vo.RecruitmentAllInfoVo;
import com.edu.boot.entity.Vo.ShopAndRecruitmentVo;
import com.edu.boot.service.RecruitmentService;
import com.edu.boot.mapper.RecruitmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 。
* @description 针对表【recruitment】的数据库操作Service实现
* @createDate 2023-11-16 13:53:20
*/
@Service
public class RecruitmentServiceImpl extends ServiceImpl<RecruitmentMapper, Recruitment>
    implements RecruitmentService{

    @Autowired
    RecruitmentMapper recruitmentMapper;

    @Override
    public List<RecruitmentAllInfoVo> getRecruitmentAndInfoById(Integer uid, Integer bid) {
        return recruitmentMapper.getRecruitmentAndInfoById(uid,bid);
    }

    @Override
    public List<Recruitment> getRecruitmentById(Integer id) {
        return recruitmentMapper.getRecruitmentById(id);
    }

    @Override
    public List<ShopAndRecruitmentVo> getShopAndRecruitmentInfoByBid(Integer bid){
        return recruitmentMapper.getShopAndRecruitmentInfoByBid(bid);
    }

    @Override
    public RecruitmentAllInfoVo getUserAllShopRecruitmentInfoByRid(Integer rid) {
        return recruitmentMapper.getUserAllShopRecruitmentInfoByRid(rid);
    }

    @Override
    public List<RecruitmentAllInfoVo> getUserAllInfo(){
        return recruitmentMapper.getUserAllInfo();
    }

    @Override
    public List<RecruitmentAllInfoVo> getUserAllInfoSearch(String search) {
        return recruitmentMapper.getUserAllInfoSearch(search);
    }
}




