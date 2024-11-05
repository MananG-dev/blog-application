package com.blogapp.services;

import com.blogapp.payloads.CommentDto;

import java.util.Set;

public interface CommentService {
    //create
    CommentDto createComment(CommentDto commentDto, Integer postId);
    //update
    //delete
    void deleteComment(Integer commentId);
    //get by user
    //get through post
    Set<CommentDto> getAllCommentsOfPost(Integer postId);
}
