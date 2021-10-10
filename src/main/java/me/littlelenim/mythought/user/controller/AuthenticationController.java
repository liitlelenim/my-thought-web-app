package me.littlelenim.mythought.user.controller;

import lombok.RequiredArgsConstructor;
import me.littlelenim.mythought.user.dto.SignUpUserDto;
import me.littlelenim.mythought.user.service.AppUserService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AppUserService appUserService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public void signUpUser(@RequestBody @Validated SignUpUserDto dto) {
        appUserService.signUpUser(dto);
    }
}
