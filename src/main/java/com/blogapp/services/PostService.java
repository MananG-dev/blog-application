package com.blogapp.services;

import com.blogapp.entities.Post;
import com.blogapp.payloads.PostDto;

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

    List<PostDto> getAllPost();

    // get all post by user
    List<PostDto> getPostByUser(Integer userId);
    //search post
    List<PostDto> searchPosts(String keyword);
}
