package com.trainee.java.repository;

import com.trainee.java.model.Post;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p WHERE p.id = :id")
    Optional<Post> findById(@Param("id") Long id);

    @Query("SELECT p FROM Post p")
    List<Post> findAll();

    @Modifying
    @Query("DELETE FROM Post p WHERE p.id = :id")
    void deleteById(@Param("id") Long id);

    @Modifying
    @Query(value = "INSERT INTO posts (title, content, user_id, url) VALUES (:title, :content, :user_id, :url)", nativeQuery = true)
    void save(@Param("title") String title, @Param("content") String content, @Param("user_id") Long userId, @Param("url") String url);

    @Modifying
    @Query("UPDATE Post p SET p.title = :title, p.content = :content, p.url = :url WHERE p.id = :id")
    void update(@Param("id") Long id, @Param("title") String title, @Param("content") String content, @Param("url") String url);



}
