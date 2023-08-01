package com.clinic.service;

import com.clinic.domain.dto.AppointmentsDto;
import com.clinic.domain.dto.DoctorScheduleDto;
import com.clinic.entity.Appointments;
import com.clinic.entity.DoctorSchedule;
import jakarta.validation.Valid;

import java.util.List;

public interface DoctorScheduleService {


    /* @Override
         public DoctorScheduleDto update(Integer id, DoctorScheduleDto doctorSchedule) {
             var result = userService.findById(id);
             result.setSchedule(DoctorScheduleMapper.toEntity(doctorSchedule));
             return DoctorScheduleMapper.toDto(result.getSchedule());
         }*/
    DoctorScheduleDto updateSchedule(Integer id, DoctorScheduleDto doctorSchedule);

    /*    DoctorScheduleDto update(Integer id,@Valid DoctorScheduleDto doctorSchedule);*/
    DoctorSchedule findById(Integer id);
    List<DoctorScheduleDto> findAll();
   /* DoctorSchedule updateSchedule( DoctorScheduleDto doctorSchedule);*/
    void deleteById(Integer id);

}
