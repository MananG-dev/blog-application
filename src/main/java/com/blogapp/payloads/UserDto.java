package com.blogapp.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {
    private Integer id;
    @NotNull
    @NotBlank
    private String name;
    @Email
    private String email;
    @NotNull
    @NotBlank
    private String password;
    @NotNull
    private String about;
}
