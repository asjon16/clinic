package com.clinic.domain.mapper;

import com.clinic.domain.dto.UserDto;
import com.clinic.entity.DoctorSchedule;
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
        var user = new UserDto();
        user.setId(u.getId());
        user.setFirstname(u.getFirstname());
        user.setLastname(u.getLastname());
        user.setEmail(u.getEmail());
        user.setRole(u.getRole().name());
        if (u.getDoctorDepartment()==null){
            user.setDepartmentsDto(null);
        }else user.setDepartmentsDto(u.getDoctorDepartment().getName());
        return user;
    }



}
