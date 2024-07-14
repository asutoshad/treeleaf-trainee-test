package com.trainee.java.repository;

import com.trainee.java.model.Comment;
import com.trainee.java.model.Post;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Repository
@Transactional
public class CommentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Comment save(Comment comment) {
        entityManager.persist(comment);
        return comment;
    }

    public List<Comment> findAll() {
        return entityManager.createQuery("SELECT c FROM Comment c", Comment.class)
                .getResultList();
    }

    public List<Comment> findByPost(Post post) {
        return entityManager.createQuery("SELECT c FROM Comment c WHERE c.post = :post", Comment.class)
                .setParameter("post", post)
                .getResultList();
    }
}
