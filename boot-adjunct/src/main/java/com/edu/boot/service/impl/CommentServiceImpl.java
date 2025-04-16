package com.edu.boot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.boot.entity.Comment;
import com.edu.boot.entity.Vo.CommentAndChildrenVo;
import com.edu.boot.entity.Vo.CommentReplyAndUserInfoVo;
import com.edu.boot.entity.Vo.CommentChildrenCommentReply;
import com.edu.boot.service.CommentService;
import com.edu.boot.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Snow
* @description 针对表【comment】的数据库操作Service实现
* @createDate 2023-11-27 21:47:32
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService{

    @Autowired
    CommentMapper commentMapper;
    @Override
    public List<CommentAndChildrenVo> getCommentByRid(Integer rid) {
        return commentMapper.getCommentByRid(rid);
    }

    @Override
    public List<CommentReplyAndUserInfoVo> getCommentReplyByCid(Integer cid) {
        return commentMapper.getCommentReplyByCid(cid);
    }

    @Override
    public List<CommentChildrenCommentReply> getAllCommentAndReply() {
        return commentMapper.getAllCommentAndReply();
    }
}




