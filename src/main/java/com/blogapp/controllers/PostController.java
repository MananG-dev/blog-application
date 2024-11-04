package com.blogapp.controllers;

import com.blogapp.entities.Post;
import com.blogapp.payloads.ApiResponse;
import com.blogapp.payloads.PostDto;
import com.blogapp.payloads.PostPaginationResponse;
import com.blogapp.services.impl.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {
    @Autowired
    private PostServiceImpl postService;

    //post - create
    @PostMapping("/u/{userId}/c/{categoryId}")
    public ResponseEntity<PostDto > createPost(@RequestBody PostDto postDto, @PathVariable(name="userId") Integer userId, @PathVariable(name="categoryId") Integer categoryId)    {
        PostDto postCreated = postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<>(postCreated, HttpStatus.CREATED);
    }
    //put - update
    @PutMapping("/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,  @PathVariable(name="") Integer postId)   {
        PostDto updatedPost = postService.updatePost(postDto,postId);
        return new ResponseEntity<PostDto>(updatedPost, HttpStatus.ACCEPTED);
    }

    //get - get by post id
    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable(name="postId") Integer postId) {
        PostDto postDto = postService.getPostById(postId);
        return new ResponseEntity<>(postDto, HttpStatus.FOUND);
    }

    //delete
    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable(name="postId") Integer postId)   {
        postService.deletePost(postId);
        ApiResponse apiResponse = ApiResponse.builder()
                .message("Post deleted successfully")
                .status(true)
                .httpStatus(HttpStatus.OK)
                .localDateTime(LocalDateTime.now())
                .build();
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

    //get - get all post by category id
    @GetMapping("/c/{categoryId}")
    public ResponseEntity<List<PostDto>> getPostByCategoryId(@PathVariable(name="categoryId") Integer categoryId) {
        List<PostDto> postDtos = postService.getPostByCategory(categoryId);
        return new ResponseEntity<>(postDtos, HttpStatus.FOUND);
    }

    //get - get all post by user id
    @GetMapping("/u/{userId}")
    public ResponseEntity<List<PostDto>> getPostByUserId(@PathVariable(name="userId") Integer userId) {
        List<PostDto> postDtos = postService.getPostByUser(userId);
        return new ResponseEntity<>(postDtos, HttpStatus.FOUND);
    }

    //get - get all posts
    @GetMapping("/")
    public ResponseEntity<List<PostDto>> getAllPosts()  {
        List<PostDto> list = postService.getAllPost();
        return new ResponseEntity<>(list, HttpStatus.FOUND);
    }

    //get - get post by page
    @GetMapping("/posts")
    public ResponseEntity<PostPaginationResponse> getPostByPage(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) Integer pageSize
    )   {
        PostPaginationResponse paginationResponse = postService.getPostByPagination(pageNumber, pageSize);
        return new ResponseEntity<>(paginationResponse, HttpStatus.OK);
    }

    //get - search post by keyword.
}
