package com.blogapp.services.impl;

import com.blogapp.entities.Comment;
import com.blogapp.entities.Post;
import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.payloads.CommentDto;
import com.blogapp.repositories.CommentRepository;
import com.blogapp.repositories.PostRepositories;
import com.blogapp.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepositories postRepositories;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        Post post = postRepositories.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));
        Comment comment = commentDtoToComment(commentDto);
        comment.setPost(post);
        Comment createdComment = commentRepository.save(comment);
        return commentToCommentDto(comment);
    }

    @Override
    public void deleteComment(Integer commentId) {

    }

    @Override
    public Set<CommentDto> getAllCommentsOfPost(Integer postId) {
        Post post = postRepositories.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));
        Set<Comment> comments = post.getComments();
        Set<CommentDto> commentDtos =comments.stream().map((s)->commentToCommentDto(s)).collect(Collectors.toCollection(HashSet::new));
        return commentDtos;
    }

    public Comment commentDtoToComment(CommentDto commentDto)   {
        Comment comment = Comment.builder()
                .commentContent(commentDto.getCommentContent())
                .build();
        return comment;
    }
    public CommentDto commentToCommentDto(Comment comment)  {
        CommentDto commentDto = CommentDto.builder()
                .commentId(comment.getCommentId())
                .commentContent(comment.getCommentContent())
                .build();
        return commentDto;
    }
}
