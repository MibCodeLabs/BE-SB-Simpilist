package com.mib.simpilist.utililty.factory;

import com.mib.simpilist.dto.Auth.AuthenticationResponse;
import com.mib.simpilist.dto.Auth.UserDto;
import com.mib.simpilist.dto.Auth.UserTokensDto;

public class AuthFactory {
    public static AuthenticationResponse buildAuthenticationResponse(UserDto userDto,
                                                                     String accessToken,
                                                                     String refreshToken){
        return AuthenticationResponse
                .builder()
                .user(userDto)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public static UserTokensDto buildUserTokensDto(String accessToken,
                                                   String refreshToken){
        return UserTokensDto
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

}
