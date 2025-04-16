package com.edu.boot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.boot.entity.Banner;
import com.edu.boot.service.BannerService;
import com.edu.boot.mapper.BannerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 。
* @description 针对表【banner】的数据库操作Service实现
* @createDate 2023-11-16 13:31:23
*/
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner>
    implements BannerService{

    @Autowired
    BannerMapper bannerMapper;
    @Override
    public List<Banner> getBannerTopList() {
        return bannerMapper.getBannerTopList();
    }
}




