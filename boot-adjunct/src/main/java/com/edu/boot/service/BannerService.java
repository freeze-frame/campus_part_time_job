package com.edu.boot.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.edu.boot.entity.Banner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 。
* @description 针对表【banner】的数据库操作Service
* @createDate 2023-11-16 13:31:23
*/
public interface BannerService extends IService<Banner> {
    List<Banner> getBannerTopList();
}
