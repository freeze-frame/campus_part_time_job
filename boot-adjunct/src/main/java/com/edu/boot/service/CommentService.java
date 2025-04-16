package com.edu.boot.service;

import com.edu.boot.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.boot.entity.Vo.CommentAndChildrenVo;
import com.edu.boot.entity.Vo.CommentReplyAndUserInfoVo;
import com.edu.boot.entity.Vo.CommentChildrenCommentReply;

import java.util.List;

/**
* @author Snow
* @description 针对表【comment】的数据库操作Service
* @createDate 2023-11-27 21:47:32
*/
public interface CommentService extends IService<Comment> {

    List<CommentAndChildrenVo> getCommentByRid(Integer rid);
    List<CommentReplyAndUserInfoVo> getCommentReplyByCid(Integer cid);

    List<CommentChildrenCommentReply> getAllCommentAndReply();
}

