package com.mib.simpilist.dto.Auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequestDto {
    @NotBlank(message = "Email must be present")
    private String email;
    @NotBlank(message = "Password must be present")
    private String password;
}
