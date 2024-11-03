package com.blogapp.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {
    private Integer id;
    //    @NotEmpty = @NotNull + @NotBlank
    @NotEmpty
    private String name;
    @NotEmpty
    @Email(message = "Email is not valid")
    private String email;
    @NotEmpty(message = "Password must not be null and empty")
    @Size(min = 3, max = 10, message = "Password must be of atleast 3 and maximum of 10 size")
//    @Pattern(regexp = )
    private String password;
    @NotNull
    private String about;
}
