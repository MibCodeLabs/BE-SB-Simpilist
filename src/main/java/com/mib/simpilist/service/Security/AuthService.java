package com.mib.simpilist.service.Security;

import com.mib.simpilist.dto.Auth.AuthenticationResponse;
import com.mib.simpilist.dto.Auth.LoginRequestDto;
import com.mib.simpilist.dto.Auth.UserTokensDto;
import com.mib.simpilist.exception.ClientException;
import com.mib.simpilist.exception.ForbiddenException;
import com.mib.simpilist.model.User;
import com.mib.simpilist.service.UserService;
import com.mib.simpilist.utililty.Utilities;
import com.mib.simpilist.utililty.factory.AuthFactory;
import com.mib.simpilist.utililty.factory.UserFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class AuthService {
    private UserService userService;
    private JwtService jwtService;
    private PasswordService passwordService;

    public AuthenticationResponse authenticate(LoginRequestDto loginRequestDto){

        User user =userService.findUserByEmailOptional(loginRequestDto.getEmail())
                .orElseThrow(() -> new ClientException("This email is not registered"));

        if(passwordService.verifyPassword(loginRequestDto.getPassword(),user.getSalt(),user.getPassword())){
            return AuthFactory.buildAuthenticationResponse(
                    UserFactory.buildUserDto(user),
                    jwtService.generateAccessToken(user.getEmail(), user.getId().toString()),
                    jwtService.generateRefreshToken(user.getEmail(), user.getId().toString())
            );
        }
        throw new ClientException("Invalid Password");
    }


    public UserTokensDto refreshTokens(UserTokensDto userTokensDto){
        if(jwtService.isTokenValid(userTokensDto.getRefreshToken())){
            return jwtService.refreshTokens(userTokensDto);
        }
        throw new ForbiddenException("Invalid Token");
    }


    public void logout(UserTokensDto userTokensDto) {
        if(Utilities.isNullOrEmpty(userTokensDto.getRefreshToken())){
            throw new ClientException("Cannot logout");
        }
        jwtService.removeRefreshToken(userTokensDto.getRefreshToken());
    }
}
