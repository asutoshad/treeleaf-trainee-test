package com.trainee.java.service.impl;

import com.trainee.java.model.Comment;
import com.trainee.java.model.Post;
import com.trainee.java.repository.CommentRepository;
import com.trainee.java.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Override
    public Comment save(Comment comment) {
       return commentRepository.save(comment);

    }

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public List<Comment> findByPost(Post post) {
        return commentRepository.findByPost(post);
    }
}
