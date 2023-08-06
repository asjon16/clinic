package com.clinic.domain.controller.rest;

import com.clinic.domain.dto.AppointmentsDto;
import com.clinic.domain.dto.DepartmentsDto;
import com.clinic.domain.dto.PatientDto;
import com.clinic.entity.Appointments;
import com.clinic.entity.Patient;
import com.clinic.service.AppointmentService;
import com.clinic.service.DoctorScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static com.clinic.domain.mapper.AppointmentsMapper.toDto;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/appointment")
public class AppointmentRestController {

    private final AppointmentService appointmentService;

    @PostMapping("/create/{id}") //does the job needs rework
    public ResponseEntity<AppointmentsDto> createAppointment(@PathVariable Integer id,@RequestBody PatientDto p){
        return ResponseEntity.ok(appointmentService.create(id,p));
    }
   @PostMapping("/create") // does her job
    public ResponseEntity<AppointmentsDto> createAppointmentNoPatients(@RequestBody AppointmentsDto a){
        return ResponseEntity.ok(appointmentService.createNoPatients(a));
    }

    @PostMapping("/creates/{patientId}") //testing
    public ResponseEntity<AppointmentsDto> createNewAppointmentWithRegisteredPatient
            (@PathVariable Integer patientId, @RequestBody AppointmentsDto appointmentsDto){
        return ResponseEntity.ok(appointmentService.createNewWithRegisteredPatient(appointmentsDto,patientId));
    }

    @GetMapping("/{id}") //not tested
    public ResponseEntity<AppointmentsDto> getAppointmentById(@PathVariable Integer id){
        return ResponseEntity.ok(toDto(appointmentService.findById(id)));
    }

    @GetMapping // not tested
    public ResponseEntity<List<AppointmentsDto>> getAppointments(){
        return ResponseEntity.ok(appointmentService.findAll());
    }

    @DeleteMapping("/{id}") // not tested
    public ResponseEntity<Void> deletePatient(@PathVariable Integer id){
        appointmentService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
