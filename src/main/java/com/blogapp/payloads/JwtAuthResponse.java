package com.blogapp.payloads;

import lombok.Data;

@Data
public class JwtAuthResponse {
    private String accessToken;
}
