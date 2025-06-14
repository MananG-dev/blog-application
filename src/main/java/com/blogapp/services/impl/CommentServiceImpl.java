package com.blogapp.services.impl;

import com.blogapp.entities.Comment;
import com.blogapp.entities.Post;
import com.blogapp.entities.User;
import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.payloads.CommentDto;
import com.blogapp.repositories.CommentRepository;
import com.blogapp.repositories.PostRepositories;
import com.blogapp.repositories.UserRepository;
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
    private UserRepository userRepository;
    @Autowired
    private PostRepositories postRepositories;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        Post post = postRepositories.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        System.out.println("post found");
        Comment comment = commentDtoToComment(commentDto);
        comment.setPost(post);
        Comment createdComment = commentRepository.save(comment);
        Set<Comment> comments = post.getComments();
        comments.add(createdComment);
        System.out.println("adding: ");
        comments.stream().forEach(e-> System.out.println(e.getCommentId()));
        post.setComments(comments);
        postRepositories.save(post);
        return commentToCommentDto(comment);
    }

    @Override
    public CommentDto createComment_Userid(CommentDto commentDto, Integer userId, Integer postId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        Post post = postRepositories.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = commentDtoToComment(commentDto);
        comment.setPost(post);
        comment.setUser(user);
        Comment createdComment = commentRepository.save(comment);
        return commentToCommentDto(createdComment);
    }

    @Override
    public CommentDto updateComment(Integer userId, Integer PostId, Integer commentId) {
        return null;
    }

    @Override
    public void deleteCommentByCommentId(Integer commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        commentRepository.delete(comment);
        return;
    }

    @Override
    public void deleteCommentByUserAndCommentId(Integer userId, Integer commentId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        if(user.getComments().contains(comment))    {
            commentRepository.delete(comment);
            return;
        }
        throw new ResourceNotFoundException("Comment", "id", commentId);
    }

    @Override
    public void deleteAllCommentByUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        user.getComments().stream().map(Comment::getCommentId).forEach((commentId)->commentRepository.deleteById(commentId));
        return;
    }

    @Override
    public void deleteCommentByPostAndCommentId(Integer postId, Integer commentId) {
        Post post = postRepositories.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        if(post.getComments().contains(comment))    {
//            TODO
            commentRepository.delete(comment);
        }
    }

    @Override
    public void deleteAllCommentOnPost(Integer postId) {
        Post post = postRepositories.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        commentRepository.deleteByPostId(postId);
        return;
    }

    @Override
    public void deleteAllCommentOnPostByUser(Integer userId, Integer postId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

    }

    @Override
    public List<CommentDto> getAllCommentsByUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        List<Comment> comments = user.getComments();
        List<CommentDto> commentDtos = comments.stream().map((c) -> commentToCommentDto(c)).toList();
        return commentDtos;
    }

    @Override
    public Set<CommentDto> getAllCommentsOfPost(Integer postId) {
        Post post = postRepositories.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        Set<Comment> comments = post.getComments();
        Set<CommentDto> commentDtos = comments.stream().map((s) -> commentToCommentDto(s)).collect(Collectors.toCollection(HashSet::new));
        return commentDtos;
    }

    public Comment commentDtoToComment(CommentDto commentDto) {
        Comment comment = Comment.builder()
                .commentContent(commentDto.getCommentContent())
                .build();
        return comment;
    }

    public CommentDto commentToCommentDto(Comment comment) {
        CommentDto commentDto = CommentDto.builder()
                .commentId(comment.getCommentId())
                .commentContent(comment.getCommentContent())
                .build();
        return commentDto;
    }
}
