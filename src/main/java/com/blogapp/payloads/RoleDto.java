package com.blogapp.payloads;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleDto {
    private String role;
}
