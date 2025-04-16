package com.edu.boot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.boot.entity.SResume;
import com.edu.boot.entity.Vo.SResumeAndNid;
import com.edu.boot.entity.Vo.SResumeAndUserVo;
import com.edu.boot.service.SResumeService;
import com.edu.boot.mapper.SResumeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 。
* @description 针对表【s_resume】的数据库操作Service实现
* @createDate 2023-11-16 13:33:45
*/
@Service
public class SResumeServiceImpl extends ServiceImpl<SResumeMapper, SResume>
    implements SResumeService{

    @Autowired
    SResumeMapper sResumeMapper;
    @Override
    public Integer getNotesConstById(Integer uid) {
        return sResumeMapper.getNotesConstById(uid);
    }

    @Override
    public List<SResumeAndUserVo> getStuNotesAndUserInfo() {
        return sResumeMapper.getStuNotesAndUserInfo();
    }

    @Override
    public List<SResumeAndNid> getAllNotesByTid(Integer tid,Integer nid) {
        return sResumeMapper.getAllNotesByTid(tid,nid);
    }

    @Override
    public SResumeAndNid getOneTid(Integer sResumeQueryWrapper) {
        return sResumeMapper.getOneTid(sResumeQueryWrapper);
    }
}




