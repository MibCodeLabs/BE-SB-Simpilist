package com.mib.simpilist.controller;

import com.mib.simpilist.dto.Auth.Registration.UserRegistrationRequest;
import com.mib.simpilist.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UsersController {
    private final UserService userService;

    @PostMapping("/register")
    public void registerNewUser(@Valid @RequestBody UserRegistrationRequest userRegistrationRequest) throws Exception {
        userService.registerNewUser(userRegistrationRequest);
    }
}