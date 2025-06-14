package com.blogapp.payloads;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CommentDto {
    private Integer commentId;
    private String commentContent;
}
