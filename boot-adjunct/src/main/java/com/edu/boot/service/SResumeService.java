package com.edu.boot.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.edu.boot.entity.SResume;
import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.boot.entity.Vo.SResumeAndNid;
import com.edu.boot.entity.Vo.SResumeAndUserVo;

import java.util.List;

/**
* @author 。
* @description 针对表【s_resume】的数据库操作Service
* @createDate 2023-11-16 13:33:45
*/
public interface SResumeService extends IService<SResume> {

    Integer getNotesConstById(Integer uid);

    List<SResumeAndUserVo> getStuNotesAndUserInfo();

    List<SResumeAndNid> getAllNotesByTid(Integer tid,Integer nid);

    SResumeAndNid getOneTid(Integer sResumeQueryWrapper);
}
