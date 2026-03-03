package com.mib.simpilist.dto.Auth;

import lombok.*;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class UserTokensDto {
    private String accessToken;
    private String refreshToken;
}
