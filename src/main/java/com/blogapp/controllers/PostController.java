package com.blogapp.controllers;

import com.blogapp.payloads.ApiResponse;
import com.blogapp.payloads.ImageResponse;
import com.blogapp.payloads.PostDto;
import com.blogapp.payloads.PostPaginationResponse;
import com.blogapp.services.FileService;
import com.blogapp.services.impl.FileServiceImpl;
import com.blogapp.services.impl.PostServiceImpl;
import com.blogapp.utils.AppConstants;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {
    @Autowired
    private PostServiceImpl postService;
    @Autowired
    private FileServiceImpl fileService;
    @Value("${project.imageDirectory}")
    private String path;

    //post - create
    @PostMapping("/u/{userId}/c/{categoryId}")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable(name = "userId") Integer userId, @PathVariable(name = "categoryId") Integer categoryId) {
        PostDto postCreated = postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<>(postCreated, HttpStatus.CREATED);
    }

    //put - update
    @PutMapping("/{postId}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable(name = "") Integer postId) {
        PostDto updatedPost = postService.updatePost(postDto, postId);
        return new ResponseEntity<PostDto>(updatedPost, HttpStatus.ACCEPTED);
    }

    //get - get by post id
    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostByPostId(@PathVariable(name = "postId") Integer postId) {
        PostDto postDto = postService.getPostById(postId);
        return new ResponseEntity<>(postDto, HttpStatus.FOUND);
    }

    //delete
    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable(name = "postId") Integer postId) {
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
    public ResponseEntity<List<PostDto>> getPostByCategoryId(@PathVariable(name = "categoryId") Integer categoryId) {
        List<PostDto> postDtos = postService.getPostByCategory(categoryId);
        return new ResponseEntity<>(postDtos, HttpStatus.FOUND);
    }

    //get - get all post by user id
    @GetMapping("/u/{userId}")
    public ResponseEntity<List<PostDto>> getPostByUserId(@PathVariable(name = "userId") Integer userId) {
        List<PostDto> postDtos = postService.getPostByUser(userId);
        return new ResponseEntity<>(postDtos, HttpStatus.FOUND);
    }

    //get - get all posts
    @GetMapping("/")
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<PostDto> list = postService.getAllPost();
        return new ResponseEntity<>(list, HttpStatus.FOUND);
    }

    //get - get post by page
    @GetMapping("/posts")
    public ResponseEntity<PostPaginationResponse> getPostByPage(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
    ) {
        PostPaginationResponse paginationResponse = postService.getPostByPagination(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(paginationResponse, HttpStatus.OK);
    }

    //get - search post by keyword.
    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<PostDto>> searchPostByKeyword(@PathVariable(name = "keyword") String keyword) {
        List<PostDto> postDtos = postService.searchPostByKeyword(keyword);
        return new ResponseEntity<>(postDtos, HttpStatus.FOUND);
    }

    //post image upload
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<ImageResponse> uploadImage(
            @PathVariable(name="postId") Integer postId,
            @RequestParam("image") MultipartFile file
            )   {
        String fileName = fileService.uploadFile(path, file);
        PostDto postDto = postService.getPostById(postId);
        postDto.setImageName(fileName);
        postDto = postService.updatePost(postDto, postId);
        ImageResponse imageResponse = ImageResponse.builder()
                .fileName(fileName)
                .status(true)
                .postDto(postDto)
                .message("Successfully uploaded")
                .build();
        return new ResponseEntity<>(imageResponse, HttpStatus.ACCEPTED);
    }

    //download image
    @GetMapping(value = "/download/{postId}", produces = MediaType.ALL_VALUE)
    public void downloadImage(@PathVariable(name="postId") Integer postId, HttpServletResponse httpServletResponse) throws IOException {
        PostDto postDto = postService.getPostById(postId);
        String fileName = postDto.getImageName();
        System.out.println(fileName);
        InputStream in = fileService.downloadFile(path, fileName);
        httpServletResponse.setContentType(MediaType.ALL_VALUE);
        StreamUtils.copy(in, httpServletResponse.getOutputStream());
    }
}
