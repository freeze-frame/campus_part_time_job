package com.edu.boot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.boot.entity.Top;
import com.edu.boot.service.TopService;
import com.edu.boot.mapper.TopMapper;
import org.springframework.stereotype.Service;

/**
* @author 。
* @description 针对表【top】的数据库操作Service实现
* @createDate 2023-11-16 13:33:56
*/
@Service
public class TopServiceImpl extends ServiceImpl<TopMapper, Top>
    implements TopService{

}




