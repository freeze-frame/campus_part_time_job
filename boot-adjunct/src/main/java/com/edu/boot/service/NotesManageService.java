package com.edu.boot.service;

import com.edu.boot.entity.NotesManage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.boot.entity.Vo.RecruitmentAndSonNotesManageVo;

import java.util.List;
import java.util.Set;

/**
* @author Snow
* @description 针对表【notes_manage】的数据库操作Service
* @createDate 2023-11-27 02:14:22
*/
public interface NotesManageService extends IService<NotesManage> {
    List<RecruitmentAndSonNotesManageVo> getNotesManageDistinctBossId(Integer bossId);


    Integer getMyNotesCount(Integer uid,Integer recid);
}
