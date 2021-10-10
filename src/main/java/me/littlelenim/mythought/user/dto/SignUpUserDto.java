package me.littlelenim.mythought.user.dto;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class SignUpUserDto {
    @Size(min = 3, max = 50)
    private final String username;
    @Size(min = 6)
    private final String password;
}
