package com.blogapp.payloads;

import com.blogapp.entities.Category;
import com.blogapp.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class PostDto {
    private Integer postId;
    private String title;
    private String content;
    private String imageName = "default.png";
    private Date addedDate;
    private UserDto user;
    private CategoryDto category;
}