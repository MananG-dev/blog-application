package com.blogapp.payloads;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class ImageResponse {
    private String message;
    private boolean status;
    private String fileName;
    private PostDto postDto;
}
