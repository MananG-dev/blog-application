package com.blogapp.payloads;

import com.blogapp.entities.Post;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class PostPaginationResponse {
    private Integer pageNumber;
    private Integer pageSize;
    private Integer totalElements;
    private Integer totalPages;
    private boolean lastPage;
    private List<PostDto> content;
}
