package com.clinic.domain.controller.rest;

import com.clinic.domain.dto.AppointmentsDto;
import com.clinic.domain.dto.DoctorScheduleDto;
import com.clinic.service.DoctorScheduleService;
import com.clinic.service.UserService;
import jdk.jfr.Registered;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.clinic.domain.mapper.DoctorScheduleMapper.toDto;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/doctor-schedule")
public class DoctorScheduleRestController {

    private final DoctorScheduleService doctorScheduleService;
    private final UserService userService;

    @GetMapping("/{id}") // works
    public ResponseEntity<DoctorScheduleDto> getDoctorScheduleById(@PathVariable Integer id){
        return ResponseEntity.ok(toDto(doctorScheduleService.findById(id)));
    }




}
