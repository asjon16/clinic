package com.clinic.service;

import com.clinic.domain.dto.*;
import com.clinic.entity.User;
import jakarta.validation.Valid;

import java.util.List;

public interface UserService {
    UserDto create(@Valid UserDto user);
    UserDto assignDoctorToDepartment (Integer doctorId, Integer departmentId);

    UserDto registerDetails(@Valid RegisterForm form);

    List<UserDto> findAllDoctorsByDepartmentId(Integer departmentId);

    DoctorScheduleDto getDoctorScheduleByDoctorId(Integer id);

    //transactional
    UserDto assignAnAppointment(Integer doctorId, Integer appointmentId);

    /*UserDto registerDetailsDoctor(@Valid RegisterForm form);*/
    UserDto update(Integer id,@Valid UserDto user);
    User findById(Integer id);
    List<UserDto> findAll();
    UserDto updateDoctorSchedule(Integer id, DoctorScheduleDto doctorScheduleDto);
    void deleteById(Integer id);





}
