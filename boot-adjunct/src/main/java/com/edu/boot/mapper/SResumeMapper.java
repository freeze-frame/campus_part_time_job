package com.edu.boot.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.edu.boot.entity.SResume;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edu.boot.entity.Vo.SResumeAndNid;
import com.edu.boot.entity.Vo.SResumeAndUserVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author 。
* @description 针对表【s_resume】的数据库操作Mapper
* @createDate 2023-11-16 13:33:45
* @Entity com.edu.boot.entity.SResume
*/
public interface SResumeMapper extends BaseMapper<SResume> {

    @Select("SELECT COUNT(*) FROM s_resume WHERE s_resume.uid = #{uid};")
    Integer getNotesConstById(Integer uid);

    @Select("SELECT * FROM `user` RIGHT JOIN `s_resume` ON user.uid = s_resume.uid WHERE user.uid = s_resume.uid;")
    List<SResumeAndUserVo> getStuNotesAndUserInfo();


    @Select("SELECT * FROM `notes_manage` LEFT JOIN s_resume ON #{tid} = s_resume.t_id  WHERE notes_manage.t_id = s_resume.t_id AND notes_manage.n_id =#{nid};")
    List<SResumeAndNid> getAllNotesByTid(Integer tid,Integer nid);

    @Select("select * from s_resume where s_resume.t_id = #{sResumeQueryWrapper}")
    SResumeAndNid getOneTid(Integer sResumeQueryWrapper);
}




