package com.trainee.java.service;

import com.trainee.java.model.Comment;
import com.trainee.java.model.Post;


import java.util.List;

public interface CommentService {

    public Comment save(Comment comment);
    public List<Comment> findAll();
    List<Comment> findByPost(Post post);
}
