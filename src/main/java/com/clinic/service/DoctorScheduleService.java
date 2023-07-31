package com.clinic.service;

import com.clinic.domain.dto.AppointmentsDto;
import com.clinic.domain.dto.DoctorScheduleDto;
import com.clinic.entity.Appointments;
import com.clinic.entity.DoctorSchedule;
import jakarta.validation.Valid;

import java.util.List;

public interface DoctorScheduleService {


    DoctorScheduleDto update(Integer id,@Valid DoctorScheduleDto doctorSchedule);
    DoctorSchedule findById(Integer id);
    List<DoctorScheduleDto> findAll();
    DoctorScheduleDto updateSchedule( DoctorScheduleDto doctorSchedule);
    void deleteById(Integer id);

}
