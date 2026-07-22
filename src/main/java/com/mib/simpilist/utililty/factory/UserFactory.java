package com.mib.simpilist.utililty.factory;

import com.mib.simpilist.dto.Auth.UserDto;
import com.mib.simpilist.model.User;

public class UserFactory {
    public static User buildRegistrationUser(UserDto userDto, String salt, String passwordHash){
        return User
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

    public static UserDto buildUserDto(User user){
        return UserDto
                .builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneCode(user.getPhoneCode())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}
