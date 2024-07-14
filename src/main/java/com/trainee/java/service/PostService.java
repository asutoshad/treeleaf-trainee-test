package com.trainee.java.service;

import com.trainee.java.model.Post;
import java.util.List;

public interface PostService {
    List<Post> findAll();
    Post findById(Long id);
    void save(Post post);
    void deleteById(Long id);
    void update(Post post);

}
