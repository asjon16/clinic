package com.clinic.service;

import com.clinic.domain.dto.*;
import com.clinic.entity.Appointments;
import com.clinic.entity.DoctorSchedule;
import com.clinic.entity.User;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface UserService {

    @Transactional
        // Works don't touch
    UserDto registerDetailsForWorker(@Valid RegisterForm form);

    @Transactional
        // Works don't touch
    UserDto registerDetailsForAdmin(@Valid RegisterForm form);

    UserDto assignDoctorToDepartment (Integer doctorId, Integer departmentId);

    UserDto registerDetails(@Valid RegisterForm form);

    List<UserDto> findAllDoctorsByDepartmentId(Integer departmentId);

    DoctorScheduleDto getDoctorScheduleByDoctorId(Integer id);

    //transactional
    UserDto assignAnAppointment(Integer doctorId, AppointmentsDto appointmentsDto, Integer patientId);

    UserDto update(Integer id,@Valid UserDto user); // update it with password encoder
    User findById(Integer id);

    UserDto findUserWithAppointmentsForDate(Integer userId, LocalDateTime date);

    List<UserDto> findAll();
    UserDto updateDoctorSchedule(Integer id, DoctorScheduleDto doctorScheduleDto);
    void deleteById(Integer id);



}
