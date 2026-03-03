package com.mib.simpilist.controller;

import com.mib.simpilist.dto.Auth.AuthenticationResponse;
import com.mib.simpilist.dto.Auth.LoginRequestDto;
import com.mib.simpilist.dto.Auth.UserTokensDto;
import com.mib.simpilist.dto.Auth.UserDto;
import com.mib.simpilist.service.Security.AuthService;
import com.mib.simpilist.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequestDto loginRequestDto) throws Exception {
        return authService.authenticate(loginRequestDto);
    }


    @PostMapping("/register")
    public AuthenticationResponse registerNewUser(@RequestBody LoginRequestDto loginRequestDto) throws Exception {
        return authService.authenticate(loginRequestDto);
    }
}
