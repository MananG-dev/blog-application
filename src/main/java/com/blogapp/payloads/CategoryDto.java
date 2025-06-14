package com.blogapp.payloads;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CategoryDto {
    private Integer categoryId;
    private String categoryTitle;
    private String categoryDescription;
}