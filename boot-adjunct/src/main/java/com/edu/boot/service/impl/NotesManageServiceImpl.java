package com.edu.boot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.boot.entity.NotesManage;
import com.edu.boot.entity.Vo.RecruitmentAndSonNotesManageVo;
import com.edu.boot.service.NotesManageService;
import com.edu.boot.mapper.NotesManageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
* @author Snow
* @description 针对表【notes_manage】的数据库操作Service实现
* @createDate 2023-11-27 02:14:22
*/
@Service
public class NotesManageServiceImpl extends ServiceImpl<NotesManageMapper, NotesManage>
    implements NotesManageService{

    @Autowired
    NotesManageMapper notesManageMapper;
    @Override
    public List<RecruitmentAndSonNotesManageVo> getNotesManageDistinctBossId(Integer bossId) {
        return notesManageMapper.getNotesManageDistinctBossId(bossId);
    }

    @Override
    public Integer getMyNotesCount(Integer uid,Integer recid) {
        return notesManageMapper.getMyNotesCount(uid,recid);
    }
}




