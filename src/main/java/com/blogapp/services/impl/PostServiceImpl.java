package com.blogapp.services.impl;

import com.blogapp.entities.Category;
import com.blogapp.entities.Comment;
import com.blogapp.entities.Post;
import com.blogapp.entities.User;
import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.payloads.*;
import com.blogapp.repositories.CategoryRepository;
import com.blogapp.repositories.CommentRepository;
import com.blogapp.repositories.PostRepositories;
import com.blogapp.repositories.UserRepository;
import com.blogapp.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepositories postRepositories;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CommentServiceImpl commentService;

    //    @Autowired
//    private ModelMapper modelMapper;
    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
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
        Post post = postRepositories.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        post.setContent(postDto.getContent());
        post.setTitle(postDto.getTitle());
        post.setImageName(postDto.getImageName());
        post = postRepositories.save(post);
        return this.postToPostDto(post);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = postRepositories.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        postRepositories.delete(post);
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = postRepositories.findById(Integer.valueOf(postId)).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        Set<CommentDto> comments = commentService.getAllCommentsOfPost(post.getPostId());
        PostDto postDto = postToPostDto(post);
        postDto.setCommentDto(comments);
        return postDto;
    }

    @Override
    public List<PostDto> getPostByCategory(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        List<Post> post = postRepositories.findByCategory(category);
        if (post == null) {
            throw new ResourceNotFoundException("Post", "category id", categoryId);
        }
        List<PostDto> postDtos = post.stream().map((p) -> this.postToPostDto(p)).toList();
        return postDtos;
    }


    @Override
    public List<PostDto> getAllPost() {
        List<Post> posts = postRepositories.findAll();
        List<PostDto> postDtos = posts.stream().map((p) -> this.postToPostDto(p)).toList();
        return postDtos;
    }

    @Override
    public PostPaginationResponse getPostByPagination(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sortOrder = sortDir.equals("dsc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sortOrder);
        Page<Post> pagePost = postRepositories.findAll(pageRequest);
        List<Post> postList = pagePost.getContent();
        List<PostDto> postDtos = postList.stream().map((p) -> this.postToPostDto(p)).toList();
        PostPaginationResponse postPaginationResponse = PostPaginationResponse.builder()
                .pageNumber(pagePost.getNumber())
                .pageSize(pagePost.getSize())
                .totalPages(pagePost.getTotalPages())
                .totalElements(pagePost.getNumberOfElements())
                .lastPage(pagePost.isLast())
                .content(postDtos)
                .build();
        return postPaginationResponse;
    }

    @Override
    public List<PostDto> getPostByUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        List<Post> posts = postRepositories.findByUser(user);
        List<PostDto> postDtos = posts.stream().map((p) -> this.postToPostDto(p)).toList();
        return postDtos;
    }

    @Override
    public List<PostDto> searchPostByKeyword(String keyword) {
//        List<PostDto> postDtos
        List<Post> posts = postRepositories.findByTitleContainingOrContentContaining(keyword, keyword);
        List<PostDto> postDtos = posts.stream().map((p)->this.postToPostDto(p)).toList();
        return postDtos;
    }

    public Post postDtoToPost(PostDto postDto) {
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
    public CommentDto commentToCommentDto(Comment comment)  {
        CommentDto commentDto = CommentDto.builder()
                .commentId(comment.getCommentId())
                .commentContent(comment.getCommentContent())
                .build();
        return commentDto;
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
