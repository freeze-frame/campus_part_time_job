package com.edu.boot.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.boot.entity.CommentReply;
import com.edu.boot.service.CommentReplyService;
import com.edu.boot.mapper.CommentReplyMapper;
import org.springframework.stereotype.Service;

/**
* @author Snow
* @description 针对表【comment_reply】的数据库操作Service实现
* @createDate 2023-11-27 21:47:32
*/
@Service
public class CommentReplyServiceImpl extends ServiceImpl<CommentReplyMapper, CommentReply>
    implements CommentReplyService{

}




