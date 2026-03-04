package com.mib.simpilist.dto.Auth.Registration;

import com.mib.simpilist.dto.Auth.UserDto;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
public class UserRegistrationRequest extends UserDto {
    @NotBlank(message = "Password cannot be blank")
    private String password;
}
