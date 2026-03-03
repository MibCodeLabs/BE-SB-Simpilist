package com.mib.simpilist.utililty.factory;

import com.mib.simpilist.dto.Auth.UserDto;
import com.mib.simpilist.model.Users;

public class UserFactory {
    public static Users buildRegistrationUser(UserDto userDto, String salt, String passwordHash){
        return Users
                .builder()
                .email(userDto.getEmail())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .password(passwordHash)
                .salt(salt)
                .phoneCode(userDto.getPhoneCode())
                .phoneNumber(userDto.getPhoneNumber())
                .build();
    }

    public static UserDto buildUserDto(Users users){
        return UserDto
                .builder()
                .id(users.getId())
                .email(users.getEmail())
                .firstName(users.getFirstName())
                .lastName(users.getLastName())
                .phoneCode(users.getPhoneCode())
                .phoneNumber(users.getPhoneNumber())
                .build();
    }
}
