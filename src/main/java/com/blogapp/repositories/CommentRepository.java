package com.blogapp.repositories;

import com.blogapp.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Modifying
    @Query("Delete From Comment c where c.post.id = :postId")
    void deleteByPostId(@Param(value = "postId") Integer postId);
}
