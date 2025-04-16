package com.edu.boot.mapper;

import com.edu.boot.entity.NotesManage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edu.boot.entity.Vo.RecruitmentAndSonNotesManageVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

/**
* @author Snow
* @description 针对表【notes_manage】的数据库操作Mapper
* @createDate 2023-11-27 02:14:22
* @Entity com.edu.boot.entity.NotesManage
*/
public interface NotesManageMapper extends BaseMapper<NotesManage> {

    @Select("SELECT * FROM `notes_manage` LEFT JOIN `user` ON notes_manage.uid = user.uid WHERE notes_manage.boss_id = #{bossId} AND notes_manage.uid = user.uid;")
    List<RecruitmentAndSonNotesManageVo> getNotesManageDistinctBossId(Integer bossId);

    @Select("SELECT COUNT(*) FROM notes_manage WHERE notes_manage.uid = #{uid} and notes_manage.rec_id = #{recid};")
    Integer getMyNotesCount(Integer uid,Integer recid);
}




