package com.edu.boot.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.boot.entity.BusinessShop;
import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.boot.entity.Recruitment;
import com.edu.boot.entity.Vo.BusinessShopAllInfoVo;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 。
* @description 针对表【business_shop】的数据库操作Service
* @createDate 2023-11-16 13:33:14
*/

public interface BusinessShopService extends IService<BusinessShop> {
    List<BusinessShopAllInfoVo>  getBusinessShopInfoById(Integer id);

    IPage<BusinessShopAllInfoVo> getBusinessShopInfo(Page<BusinessShopAllInfoVo> page);

    IPage<BusinessShopAllInfoVo>  getBusinessShopInfoByLikeName(Page<BusinessShopAllInfoVo> page,String str);

    IPage<BusinessShopAllInfoVo> getBusinessShopIdentity(Page<BusinessShopAllInfoVo> page, QueryWrapper<BusinessShopAllInfoVo> queryWrapper);

//    List<Recruitment> getAllRecIdByMy();
}
