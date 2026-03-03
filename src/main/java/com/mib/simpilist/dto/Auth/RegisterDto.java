package com.mib.simpilist.dto.Auth;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterDto {
    private String firstName;
    private String lastName;
    private String phoneCode;
    private String phoneNumber;
    private String email;
    private String password;
}
