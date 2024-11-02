package com.blogapp.payloads;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {

    private Integer id;
    private String name;
    private String email;
    private String password;
    private String about;
}
