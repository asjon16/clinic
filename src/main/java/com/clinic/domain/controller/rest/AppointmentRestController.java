package com.clinic.domain.controller.rest;

import com.clinic.domain.dto.AppointmentsDto;
import com.clinic.domain.dto.DepartmentsDto;
import com.clinic.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.clinic.domain.mapper.AppointmentsMapper.toDto;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/appointment")
public class AppointmentRestController {

    private final AppointmentService appointmentService;
    @PostMapping("/create")
    public ResponseEntity<AppointmentsDto> createAppointment(@RequestBody AppointmentsDto a){
        return ResponseEntity.ok(appointmentService.create(a));
    }
   @PostMapping("/test")
    public ResponseEntity<AppointmentsDto> createAppointmentNoPatients(@RequestBody AppointmentsDto a){
        return ResponseEntity.ok(appointmentService.createNoPatients(a));
    }


    @GetMapping("/{id}")
    public ResponseEntity<AppointmentsDto> getAppointmentById(@PathVariable Integer id){
        return ResponseEntity.ok(toDto(appointmentService.findById(id)));
    }

    @GetMapping
    public ResponseEntity<List<AppointmentsDto>> getAppointments(){
        return ResponseEntity.ok(appointmentService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Integer id){
        appointmentService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
