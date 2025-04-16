package com.edu.boot.mapper;

import com.edu.boot.entity.Banner;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author 。
* @description 针对表【banner】的数据库操作Mapper
* @createDate 2023-11-16 13:31:23
* @Entity com.edu.boot.entity.Banner
*/
public interface BannerMapper extends BaseMapper<Banner> {

    @Select("SELECT * FROM banner,(SELECT rec_id FROM `recruitment` ORDER BY rec_heat DESC LIMIT 7) AS rec WHERE banner.rec_id = rec.rec_id;")
    List<Banner> getBannerTopList();
}




