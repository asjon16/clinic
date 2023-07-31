package com.clinic.service.impl;

import com.clinic.domain.dto.DoctorScheduleDto;
import com.clinic.domain.exception.ResourceNotFoundException;
import com.clinic.domain.mapper.DoctorScheduleMapper;
import com.clinic.domain.mapper.UserMapper;
import com.clinic.entity.DoctorSchedule;
import com.clinic.repository.DoctorScheduleRepository;
import com.clinic.repository.UserRepository;
import com.clinic.service.DoctorScheduleService;
import com.clinic.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import com.clinic.domain.mapper.AppointmentsMapper;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Validated
@Service
public class DoctorScheduleServiceImpl implements DoctorScheduleService {

    private final DoctorScheduleRepository doctorScheduleRepository;
    private final UserService userService;
    private final UserRepository userRepository;


    @Override
    public DoctorScheduleDto update(Integer id, DoctorScheduleDto doctorSchedule) {
        var result = userService.findById(id);
        result.setSchedule(DoctorScheduleMapper.toEntity(doctorSchedule));
        return DoctorScheduleMapper.toDto(result.getSchedule());
    }
    @Override
    public DoctorScheduleDto updateSchedule( DoctorScheduleDto doctorSchedule) {
       var result = DoctorScheduleMapper.toEntity(doctorSchedule);
       doctorScheduleRepository.save(result);
       return DoctorScheduleMapper.toDto(result);
    }


    @Override
    public DoctorSchedule findById(Integer id) {
       var result = doctorScheduleRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(String
               .format("Schedule with id %s doesnt exist",id)));
       return result;
    }

    @Override
    public List<DoctorScheduleDto> findAll() {
        return doctorScheduleRepository.findAll().stream().map(DoctorScheduleMapper::toDto).collect(Collectors.toList());
    }



    @Override
    public void deleteById(Integer id) {
        var toDelete= findById(id);
        doctorScheduleRepository.delete(toDelete);
    }
}
