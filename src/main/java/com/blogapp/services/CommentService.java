package com.blogapp.services;

import com.blogapp.entities.Comment;
import com.blogapp.payloads.CommentDto;

import java.util.List;
import java.util.Set;

public interface CommentService {
    //create
    CommentDto createComment(CommentDto commentDto, Integer postId);
    CommentDto createComment_Userid(CommentDto commentDto, Integer userId, Integer postId);

    //update
    CommentDto updateComment(Integer userId, Integer PostId, Integer commentId);

    //delete
    void deleteCommentByCommentId(Integer commentId);
    void deleteCommentByUserAndCommentId(Integer userId, Integer commentId);
    void deleteAllCommentByUser(Integer userId);
    void deleteCommentByPostAndCommentId(Integer postId, Integer commentId);
    void deleteAllCommentOnPost(Integer postId);
    void deleteAllCommentOnPostByUser(Integer userId, Integer postId);

    //get by user
    List<CommentDto> getAllCommentsByUser(Integer userId);

    //get through post
    Set<CommentDto> getAllCommentsOfPost(Integer postId);
}
