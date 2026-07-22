package com.mib.simpilist.controller;

import com.mib.simpilist.dto.Auth.AuthenticationResponse;
import com.mib.simpilist.dto.Auth.LoginRequestDto;
import com.mib.simpilist.dto.Auth.Registration.UserRegistrationRequest;
import com.mib.simpilist.dto.Auth.UserTokensDto;
import com.mib.simpilist.service.Security.AuthService;
import com.mib.simpilist.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @CrossOrigin
    @PostMapping("/login")
    public AuthenticationResponse login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        return authService.authenticate(loginRequestDto);
    }

    @CrossOrigin
    @PostMapping("/logout")
    public void logout(@Valid @RequestBody UserTokensDto userTokensDto) {
        authService.logout(userTokensDto);
    }

    @CrossOrigin
    @PostMapping("/register")
    public void registerNewUser(@Valid @RequestBody UserRegistrationRequest userRegistrationRequest) throws Exception {
        userService.registerNewUser(userRegistrationRequest);
    }

    @CrossOrigin
    @PostMapping("/refresh")
    public UserTokensDto refreshToken(@RequestBody UserTokensDto userTokensDto) throws Exception {
        return authService.refreshTokens(userTokensDto);
    }
}
