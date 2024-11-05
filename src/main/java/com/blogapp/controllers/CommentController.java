package com.blogapp.controllers;

import com.blogapp.payloads.CommentDto;
import com.blogapp.services.CommentService;
import com.blogapp.services.impl.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    private CommentServiceImpl commentService;
    //create
    @PostMapping("/{postId}")
    public ResponseEntity<CommentDto> createComment(@RequestBody(required = true) CommentDto commentDto,
                                                    @PathVariable(name="postId") Integer postId)  {
        CommentDto commentDto1 = commentService.createComment(commentDto, postId);
        return new ResponseEntity<>(commentDto1, HttpStatus.CREATED);
    }

    //get by post id
    @GetMapping("/{postId}")
    public ResponseEntity<Set<CommentDto>> getCommentByPostId(@PathVariable(name="postId") Integer postId)  {
        Set<CommentDto> commentDtos = commentService.getAllCommentsOfPost(postId);
        return new ResponseEntity<>(commentDtos, HttpStatus.CREATED);
    }

}
