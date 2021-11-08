package me.littlelenim.mythought.user.dto;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Validated
public class SignUpUserDto {
    @Size(min = 3, max = 50)
    @NotBlank
    private final String username;
    @Size(min = 6)
    @NotBlank
    private final String password;
}
