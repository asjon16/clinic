package com.clinic.domain.mapper;

import com.clinic.domain.dto.UserDto;
import com.clinic.entity.Appointments;
import com.clinic.entity.DaysOff;
import com.clinic.entity.User;
import com.clinic.entity.Role;

import java.util.stream.Collectors;

public class UserMapper {
    public static User toEntity(UserDto u){
        return User.builder()
                .firstname(u.getFirstname())
                .lastname(u.getLastname())
                .email(u.getEmail())
                .password(u.getPassword())
                .role(Role.fromValue(u.getRole()))
                .build();
    }


    public static UserDto toDto(User u){
        var user = new UserDto();
        user.setId(u.getId());
        user.setFirstname(u.getFirstname());
        user.setLastname(u.getLastname());
        user.setEmail(u.getEmail());
        user.setRole(u.getRole().name());
        user.setDaysOffDto(u.getDaysOff().stream().map(DaysOff::toString).collect(Collectors.toList()));
        if (u.getDoctorDepartment()==null){
            user.setDepartmentsDto(null);
        }else user.setDepartmentsDto(u.getDoctorDepartment().getName());
        if (u.getSchedule()==null){
            user.setDoctorSchedule(null);
        }else {
            user.setDoctorSchedule(u.getSchedule().toString());
            if (u.getSchedule().getAppointments()==null){
                user.setAppointments(null);
            }else {
                    user.setAppointments(u.getSchedule().getAppointments()
                            .stream().map(Appointments::toString).collect(Collectors.toList()));
            }
        }
        return user;
    }

// bej krijimin e schedules ne momentin qe krijohet doktori


}
