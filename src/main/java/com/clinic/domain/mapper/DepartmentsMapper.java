package com.clinic.domain.mapper;

import com.clinic.domain.dto.DepartmentsDto;
import com.clinic.domain.dto.PatientDto;
import com.clinic.domain.dto.UserDto;
import com.clinic.entity.Departments;
import com.clinic.entity.Gender;
import com.clinic.entity.Patient;
import com.clinic.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DepartmentsMapper {
    public static Departments toEntity(DepartmentsDto d){
        List<User> users = new ArrayList<>();
        for (UserDto userDto : d.getDoctor()){
            User user = new User();
            user.setId(userDto.getId());
            user.setFirstname(userDto.getFirstname());
            user.setLastname(userDto.getLastname());
            user.setEmail(userDto.getEmail());
            user.setPassword(userDto.getPassword());
            Departments departments = new Departments();
            departments.setName(d.getName());
            user.setDoctorDepartment(departments);
            users.add(user);
        } return new Departments(d.getId(), d.getName(), users);
    }
    public static Departments toEntityNoUsers(DepartmentsDto d){
        return new Departments(d.getName());

    }
    public static DepartmentsDto toDtoNoUsers(Departments d){
        return new DepartmentsDto(d.getId(),d.getName());

    }
    public static DepartmentsDto toDto(Departments d){
        List<UserDto> userDtos = new ArrayList<>();
        for (User users : d.getDoctor()){
            UserDto userDto = new UserDto();
            userDto.setId(users.getId());
            userDto.setFirstname(users.getFirstname());
            userDto.setLastname(users.getLastname());
            userDto.setEmail(users.getEmail());
            userDto.setPassword(users.getPassword());
            DepartmentsDto departmentsDto = new DepartmentsDto();
            departmentsDto.setName(d.getName());
            userDto.setDepartmentsDto(departmentsDto);
            userDtos.add(userDto);
        } return new DepartmentsDto(d.getId(), d.getName(), userDtos);

    }
}
