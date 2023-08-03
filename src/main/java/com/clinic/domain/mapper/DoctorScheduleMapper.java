package com.clinic.domain.mapper;

import com.clinic.domain.dto.DoctorScheduleDto;
import com.clinic.entity.Appointments;
import com.clinic.entity.DoctorSchedule;

import java.util.Objects;
import java.util.stream.Collectors;

public class DoctorScheduleMapper {

    public static DoctorSchedule toEntity (DoctorScheduleDto d){
        DoctorSchedule doctorSchedule = new DoctorSchedule();
        doctorSchedule.setStartTime(d.getStartTime());
        doctorSchedule.setEndTime(d.getStartTime());
        return doctorSchedule;
    }

    public static DoctorScheduleDto toDto (DoctorSchedule d){
        DoctorScheduleDto doctorScheduleDto = new DoctorScheduleDto();
        doctorScheduleDto.setId(d.getId());
        doctorScheduleDto.setStartTime(d.getStartTime());
        doctorScheduleDto.setEndTime(d.getEndTime());
        doctorScheduleDto.setDoctor(d.getDoctor().getLastname());
        doctorScheduleDto.setAppointments(d.getAppointments().stream().map(Appointments::toString).collect(Collectors.toList()));
        return doctorScheduleDto;
    }
    public static DoctorSchedule toUpdate(DoctorSchedule u, DoctorScheduleDto d){
        u.setStartTime(d.getStartTime());
        u.setEndTime(d.getEndTime());
        return u;
    }


}
