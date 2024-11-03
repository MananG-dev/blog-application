package com.blogapp.payloads;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApiResponse {
    private String message;
    private Boolean status;
}
