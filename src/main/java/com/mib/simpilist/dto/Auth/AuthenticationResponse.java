package com.mib.simpilist.dto.Auth;

import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class AuthenticationResponse extends UserTokensDto{
    private UserDto user;
}
