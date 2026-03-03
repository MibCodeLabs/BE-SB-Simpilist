package com.mib.simpilist.dto.Auth.Registration;

import com.mib.simpilist.dto.Auth.UserDto;
import lombok.*;

@Getter
@Setter
public class UserRegistrationRequest extends UserDto {
    private String password;
}
