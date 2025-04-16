package com.edu.boot.mapper;

import com.edu.boot.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.edu.boot.entity.Vo.CommentAndChildrenVo;
import com.edu.boot.entity.Vo.CommentReplyAndUserInfoVo;
import com.edu.boot.entity.Vo.CommentChildrenCommentReply;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author Snow
* @description 针对表【comment】的数据库操作Mapper
* @createDate 2023-11-27 21:47:32
* @Entity com.edu.boot.entity.Comment
*/
public interface CommentMapper extends BaseMapper<Comment> {

    //通过rid获取这个简历的所有父级评论 附带 用户信息
    @Select("SELECT user.uid,user.username,user.image_data,`comment`.* FROM `comment` LEFT JOIN `user` ON comment.uid = user.uid WHERE comment.rec_id = #{rid} AND comment.uid = user.uid;")
    List<CommentAndChildrenVo> getCommentByRid(Integer rid);

    //通过comment表 搜索出他的子评论表，再连接这用户的uid信息
    @Select("SELECT user.uid,user.username,user.image_data,`comment_reply`.* FROM  `comment_reply` LEFT JOIN `user` ON comment_reply.uid = user.uid WHERE comment_reply.answer_id = #{cid} AND comment_reply.uid = user.uid;")
    List<CommentReplyAndUserInfoVo> getCommentReplyByCid(Integer cid);

    @Select("SELECT * FROM `comment`;")
    List<CommentChildrenCommentReply> getAllCommentAndReply();
}




