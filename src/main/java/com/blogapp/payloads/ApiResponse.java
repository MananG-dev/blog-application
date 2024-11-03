package com.blogapp.payloads;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ApiResponse {
    private String message;
    private LocalDateTime localDateTime;
    private HttpStatus httpStatus;
    private Boolean status;
}
