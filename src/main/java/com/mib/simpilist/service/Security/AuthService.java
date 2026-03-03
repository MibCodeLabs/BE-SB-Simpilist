package com.mib.simpilist.service.Security;

import com.mib.simpilist.dto.Auth.AuthenticationResponse;
import com.mib.simpilist.dto.Auth.LoginRequestDto;
import com.mib.simpilist.exception.ClientException;
import com.mib.simpilist.exception.Forbidden;
import com.mib.simpilist.model.Users;
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

        Users users =userService.findUserByEmailOptional(loginRequestDto.getEmail())
                .orElseThrow(() -> new ClientException("This email is not registered"));

        validatePassword(users,loginRequestDto);

        return AuthFactory.buildAuthenticationResponse(
                UserFactory.buildUserDto(users),
                jwtService.generateAccessToken(users),
                jwtService.generateRefreshToken(users)
        );
    }

    private void validatePassword(Users users, LoginRequestDto loginRequestDto){
        //add numbered retries and account ban after multiple invalid retries
        if(!passwordService.verifyPassword(loginRequestDto.getPassword(),
                users.getPassword(),
                users.getSalt())){
            throw new Forbidden("Invalid Password");
        }
    }
}
