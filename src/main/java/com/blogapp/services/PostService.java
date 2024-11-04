package com.blogapp.services;

import com.blogapp.payloads.PostDto;
import com.blogapp.payloads.PostPaginationResponse;

import java.util.List;

public interface PostService {
    //create
    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
    //update
    PostDto updatePost(PostDto postDto, Integer postId);
    //delete
    void deletePost(Integer postId);
    // get
    PostDto getPostById(Integer postId);
    // get all post by category
    List<PostDto> getPostByCategory(Integer categoryId);
    // get all post by user
    List<PostDto> getPostByUser(Integer userId);
    // get all posts
    List<PostDto> getAllPost();
    // get posts based on pagination
    PostPaginationResponse getPostByPagination(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
    //search post
    List<PostDto> searchPostByKeyword(String keyword);
}
