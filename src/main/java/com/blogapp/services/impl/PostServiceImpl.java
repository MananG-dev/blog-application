package com.blogapp.services.impl;

import com.blogapp.entities.Category;
import com.blogapp.entities.Post;
import com.blogapp.entities.User;
import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.payloads.CategoryDto;
import com.blogapp.payloads.PostDto;
import com.blogapp.payloads.UserDto;
import com.blogapp.repositories.CategoryRepository;
import com.blogapp.repositories.PostRepositories;
import com.blogapp.repositories.UserRepository;
import com.blogapp.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepositories postRepositories;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    //    @Autowired
//    private ModelMapper modelMapper;
    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
        User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "id", userId));
        Category category = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "id", categoryId));
        Post post = this.postDtoToPost(postDto);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setCategory(category);
        Post post1 = postRepositories.save(post);
        return this.postToPostDto(post1);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = postRepositories.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));
        post.setContent(postDto.getContent());
        post.setTitle(postDto.getTitle());
        post = postRepositories.save(post);
        return this.postToPostDto(post);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = postRepositories.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));
        postRepositories.delete(post);
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = postRepositories.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));
        return this.postToPostDto(post);
    }

    @Override
    public List<PostDto> getPostByCategory(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "id", categoryId));
        List<Post> post = postRepositories.findByCategory(category);
        if(post==null)  {
            throw new ResourceNotFoundException("Post", "category id", categoryId);
        }
        List<PostDto> postDtos = post.stream().map((p)->this.postToPostDto(p)).toList();
        return postDtos;
    }


    @Override
    public List<PostDto> getAllPost() {
        List<Post> posts = postRepositories.findAll();
        List<PostDto> postDtos = posts.stream().map((p)->this.postToPostDto(p)).toList();
        return postDtos;
    }

    @Override
    public List<PostDto> getPostByUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "id", userId));
        List<Post> posts = postRepositories.findByUser(user);
        List<PostDto> postDtos = posts.stream().map((p)->this.postToPostDto(p)).toList();
        return postDtos;
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        return List.of();
    }

    public Post postDtoToPost(PostDto postDto)  {
        Post post = Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .build();
        return post;
    }
    public PostDto postToPostDto(Post post) {
        PostDto postDto = PostDto.builder()
                .postId(post.getPostId())
                .content(post.getContent())
                .title(post.getTitle())
                .addedDate(post.getAddedDate())
                .imageName(post.getImageName())
                .user(userToUserDto(post.getUser()))
                .category(categoryToCategoryDto(post.getCategory()))
                .build();
        return postDto;
    }
    public UserDto userToUserDto(User user) {
        UserDto userDto = UserDto.builder().id(user.getId()).name(user.getName()).email(user.getEmail()).password(user.getPassword()).about(user.getAbout()).build();
        return userDto;
    }

    public CategoryDto categoryToCategoryDto(Category category) {
        CategoryDto categoryDto = CategoryDto.builder()
                .categoryId(category.getCategoryId())
                .categoryTitle(category.getCategoryTitle())
                .categoryDescription(category.getCategoryDescription())
                .build();
        return categoryDto;
    }
}
