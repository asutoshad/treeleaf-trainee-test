package com.trainee.java.service.impl;

import com.trainee.java.model.Post;
import com.trainee.java.repository.PostRepository;
import com.trainee.java.service.PostService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    @Transactional
    public Post findById(Long id) {
        Post post = postRepository.findById(id).orElse(null);
        if (post != null) {

            post.getComments().size();
        }
        return post;
    }

    @Override
    @Transactional
    public void save(Post post) {
        postRepository.save(post.getTitle(), post.getContent(), post.getUser().getId(), post.getUrl());
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }

    @Transactional
    public void update(Post post) {
        postRepository.update(post.getId(), post.getTitle(), post.getContent(), post.getUrl());
    }
}
