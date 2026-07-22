package com.mib.simpilist.dto.Auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "First name cannot be blank")
    private String firstName;
    @NotBlank(message = "Last name cannot be blank")
    private String lastName;
    @NotBlank(message = "Phone code cannot be blank")
    private String phoneCode;
    @NotBlank(message = "Phone number cannot be blank")
    private String phoneNumber;
    @NotBlank(message = "Email cannot be blank")
    private String email;
}
