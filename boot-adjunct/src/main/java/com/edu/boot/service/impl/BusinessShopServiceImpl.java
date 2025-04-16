package com.edu.boot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.boot.entity.BusinessShop;
import com.edu.boot.entity.Vo.BusinessShopAllInfoVo;
import com.edu.boot.service.BusinessShopService;
import com.edu.boot.mapper.BusinessShopMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 。
* @description 针对表【business_shop】的数据库操作Service实现
* @createDate 2023-11-16 13:33:14
*/
@Service
public class BusinessShopServiceImpl extends ServiceImpl<BusinessShopMapper, BusinessShop>
    implements BusinessShopService{
    @Resource
    private BusinessShopMapper businessShopMapper;

    public List<BusinessShopAllInfoVo> getBusinessShopInfoById(Integer id){
        return businessShopMapper.getBusinessShopInfoById(id);
    }
    public IPage<BusinessShopAllInfoVo> getBusinessShopInfoByLikeName(Page<BusinessShopAllInfoVo> page,String str){
        return businessShopMapper.getBusinessShopInfoByLikeName(page,str);
    }

    public IPage<BusinessShopAllInfoVo> getBusinessShopIdentity(Page<BusinessShopAllInfoVo> page, QueryWrapper<BusinessShopAllInfoVo> queryWrapper) {
        return businessShopMapper.getBusinessShopIdentity(page,queryWrapper);
    }

    public IPage<BusinessShopAllInfoVo> getBusinessShopInfo(Page<BusinessShopAllInfoVo> page){
        return businessShopMapper.getBusinessShopInfo(page);
    }

}




