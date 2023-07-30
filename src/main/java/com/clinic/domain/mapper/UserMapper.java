package com.clinic.domain.mapper;

import com.clinic.domain.dto.UserDto;
import com.clinic.entity.User;
import com.clinic.entity.UserRole;

public class UserMapper {
    public static User toEntity(UserDto u){
        return User.builder()
                .firstname(u.getFirstname())
                .lastname(u.getLastname())
                .email(u.getEmail())
                .password(u.getPassword())
                .role(UserRole.fromValue(u.getRole()))
                .build();
    }
    public static UserDto toDto(User u){
        return UserDto.builder()
                .id(u.getId())
                .firstname(u.getFirstname())
                .lastname(u.getLastname())
                .email(u.getEmail())
                .role(u.getRole().name())
                .build();
    }

}
