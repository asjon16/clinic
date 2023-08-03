package com.clinic.domain.mapper;

import com.clinic.domain.dto.UserDto;
import com.clinic.entity.Appointments;
import com.clinic.entity.DoctorSchedule;
import com.clinic.entity.User;
import com.clinic.entity.UserRole;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

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

    //testing if i can create doctor with a prepared schedule
    public static User atoEntity(UserDto u){
        var user = new User();
        user.setFirstname(u.getFirstname());
        user.setLastname(u.getLastname());
        user.setEmail(u.getEmail());
        user.setPassword(u.getPassword());
        user.setRole(UserRole.fromValue(u.getRole()));
        if (user.getRole().equals(UserRole.DOCTOR)){
            user.setSchedule(new DoctorSchedule(LocalDateTime.now(),LocalDateTime.now().plusHours(8)));
        }
        return user;
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
        if (u.getSchedule()==null){
            user.setDoctorSchedule(null);
        }else {
            user.setDoctorSchedule(u.getSchedule().getId().toString());
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
