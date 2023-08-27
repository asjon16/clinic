package com.clinic.service;

import com.clinic.domain.dto.*;
import com.clinic.entity.Appointments;
import com.clinic.entity.DaysOff;
import com.clinic.entity.DoctorSchedule;
import com.clinic.entity.User;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface UserService {


    UserDto deleteDayOffForUser(Integer userId, Integer dayOffId);

    // Works don't touch
    UserDto registerDetailsForWorker(@Valid RegisterForm form);

    UserDto changePassword(Integer userId, NewPasswordAdminOnly password);

        // Works don't touch
    UserDto registerDetailsForAdmin(@Valid RegisterForm form);

    UserDto assignDoctorToDepartment (Integer doctorId, Integer departmentId);

    UserDto registerDetails(@Valid RegisterForm form);

    List<UserDto> findAllDoctorsByDepartmentId(Integer departmentId);

    DoctorScheduleDto getDoctorScheduleByDoctorId(Integer id);

    UserDto update(Integer id, @Valid UserDto user); // update it with password encoder
    User findById(Integer id);

    List<UserDto> findAll();
    UserDto updateDoctorSchedule(Integer id, DoctorScheduleDto doctorScheduleDto);
    UserDto setDaysOffForUser(DaysOffDto dates, Integer userId);
    UserDto updatePassword(PasswordChanger passwordChanger);

    List <UserDto> findAllByFirstname(String name);
    List <UserDto> findAllByLastname(String name);
    List<UserDto> findDoctorWithMostAppointments();

    void deleteById(Integer id);
    void deleteByDeletedTrue();

    List <UserDto> doctorThatPatientVisitsTheMost(Integer patientId);





}
