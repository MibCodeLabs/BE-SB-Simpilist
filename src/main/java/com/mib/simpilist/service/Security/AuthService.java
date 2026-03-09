package com.mib.simpilist.service.Security;

import com.mib.simpilist.dto.Auth.AuthenticationResponse;
import com.mib.simpilist.dto.Auth.LoginRequestDto;
import com.mib.simpilist.exception.ClientException;
import com.mib.simpilist.exception.Forbidden;
import com.mib.simpilist.model.User;
import com.mib.simpilist.service.UserService;
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

        validatePassword(user,loginRequestDto);

        return AuthFactory.buildAuthenticationResponse(
                UserFactory.buildUserDto(user),
                jwtService.generateAccessToken(user),
                jwtService.generateRefreshToken(user)
        );
    }

    private void validatePassword(User user, LoginRequestDto loginRequestDto){
        //todo: add numbered retries and account ban after multiple invalid retries
        if(!passwordService.verifyPassword(loginRequestDto.getPassword(),
                user.getSalt(),
                user.getPassword())
        ){
            throw new Forbidden("Invalid Password");
        }
    }
}
