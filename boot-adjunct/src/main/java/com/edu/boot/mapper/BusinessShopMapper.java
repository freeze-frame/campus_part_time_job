package com.edu.boot.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.edu.boot.entity.BusinessShop;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edu.boot.entity.Vo.BusinessShopAllInfoVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author 。
* @description 针对表【business_shop】的数据库操作Mapper
* @createDate 2023-11-16 13:33:14
* @Entity com.edu.boot.entity.BusinessShop
*/
public interface BusinessShopMapper extends BaseMapper<BusinessShop> {

    @Select("SELECT * FROM user LEFT JOIN business_shop ON #{id}=business_shop.uid WHERE business_shop.uid = user.uid")
    List<BusinessShopAllInfoVo> getBusinessShopInfoById(Integer id);
    @Select("SELECT * FROM user LEFT JOIN business_shop ON user.uid=business_shop.uid WHERE business_shop.uid = user.uid")
    IPage<BusinessShopAllInfoVo> getBusinessShopInfo(Page<BusinessShopAllInfoVo> page);
    @Select("SELECT * FROM user RIGHT JOIN (SELECT * FROM business_shop WHERE business_shop.b_name LIKE #{str})AS b ON user.uid=b.uid WHERE user.pid = 3 AND user.uid=b.uid")
    IPage<BusinessShopAllInfoVo> getBusinessShopInfoByLikeName(Page<BusinessShopAllInfoVo> page,String str);

    @Select("SELECT * FROM business_shop WHERE business_shop.b_identity = 0")
    IPage<BusinessShopAllInfoVo> getBusinessShopIdentity(Page<BusinessShopAllInfoVo> page, QueryWrapper<BusinessShopAllInfoVo> queryWrapper);
}




