package com.clinic.service.impl;

import com.clinic.configuration.SecurityUtils;
import com.clinic.domain.dto.DoctorScheduleDto;
import com.clinic.domain.exception.ResourceNotFoundException;
import com.clinic.domain.mapper.DoctorScheduleMapper;
import com.clinic.entity.DoctorSchedule;
import com.clinic.entity.Role;
import com.clinic.repository.DoctorScheduleRepository;
import com.clinic.repository.UserRepository;
import com.clinic.service.DoctorScheduleService;
import com.clinic.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Validated
@Service
public class DoctorScheduleServiceImpl implements DoctorScheduleService {

    private final DoctorScheduleRepository doctorScheduleRepository;



    @Override
    public DoctorScheduleDto updateSchedule(Integer id, DoctorScheduleDto doctorSchedule) {
       var result = findById(id);
       var schedule =doctorScheduleRepository.save(DoctorScheduleMapper.toUpdate(result,doctorSchedule));
       return DoctorScheduleMapper.toDto(schedule);
    }


    @Override
    public DoctorSchedule findById(Integer id) {
        return doctorScheduleRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(String
                .format("Schedule with id %s doesnt exist",id)));
    }

    @Override
    public boolean isAppointmentWithinDoctorSchedule(Integer scheduleId, LocalDateTime appointmentStartTime, LocalDateTime appointmentEndTime) {
        var schedule = findById(scheduleId);
        LocalTime startTime = appointmentStartTime.toLocalTime();
        LocalTime endTime = appointmentEndTime.toLocalTime();
        return !startTime.isBefore(schedule.getStartTime()) && !endTime.isAfter(schedule.getEndTime());
    }


}
