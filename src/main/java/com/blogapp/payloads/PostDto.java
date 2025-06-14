package com.blogapp.payloads;

import com.blogapp.entities.Category;
import com.blogapp.entities.User;
import lombok.*;

import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PostDto {
    private Integer postId;
    private String title;
    private String content;
    private String imageName = "default.png";
    private Date addedDate;
    private UserDto user;
    private CategoryDto category;
    private Set<CommentDto> commentDto;
}