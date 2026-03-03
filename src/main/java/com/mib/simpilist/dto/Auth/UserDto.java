package com.mib.simpilist.dto.Auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneCode;
    private String phoneNumber;
    private String email;
}
