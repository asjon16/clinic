package com.clinic.service;

import com.clinic.domain.dto.AppointmentsDto;
import com.clinic.domain.dto.DoctorScheduleDto;
import com.clinic.entity.Appointments;
import com.clinic.entity.DoctorSchedule;
import com.clinic.entity.User;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.List;

public interface DoctorScheduleService {


    DoctorScheduleDto updateSchedule(Integer id, DoctorScheduleDto doctorSchedule);

    DoctorSchedule findById(Integer id);
    void deleteById(Integer id);




}
