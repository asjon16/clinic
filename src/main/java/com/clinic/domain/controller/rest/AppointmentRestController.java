package com.clinic.domain.controller.rest;

import com.clinic.domain.dto.AppointmentsDto;
import com.clinic.domain.dto.DepartmentsDto;
import com.clinic.domain.dto.PatientDto;
import com.clinic.domain.dto.UserDto;
import com.clinic.domain.mapper.AppointmentsMapper;
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
import java.util.stream.Collectors;

import static com.clinic.domain.mapper.AppointmentsMapper.toDto;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/appointment")
public class AppointmentRestController {

    private final AppointmentService appointmentService;

    @PostMapping("/doctor/schedule/{doctorId}") // Works
    public ResponseEntity<AppointmentsDto> addAppointmentToDoctorSchedules(@PathVariable Integer doctorId, @RequestBody AppointmentsDto appointmentId, @RequestParam Integer patientId){
        return ResponseEntity.ok(appointmentService.assignAnAppointment(doctorId,appointmentId,patientId));
    }

    @PutMapping("/update/{id}") //works
    public ResponseEntity<AppointmentsDto> updateAppointmentWithId
            (@PathVariable Integer id, @RequestBody AppointmentsDto appointmentsDto){
        return ResponseEntity.ok(appointmentService.updateById(id,appointmentsDto));
    }

    @GetMapping("/{id}") //works
    public ResponseEntity<AppointmentsDto> getAppointmentById(@PathVariable Integer id){
        return ResponseEntity.ok(toDto(appointmentService.findById(id)));
    }
    @GetMapping("/patient/{id}") //works
    public ResponseEntity<List<AppointmentsDto>> getPatientAppointments(@PathVariable Integer id){
        return ResponseEntity.ok(appointmentService.findAllAppointmentByPatient_id(id));
    }
    @PostMapping("/all/{id}") //not tested
    public ResponseEntity<List<AppointmentsDto>> getPatientAppointmentsBetweenTheDates(@PathVariable Integer id, @RequestBody AppointmentsDto appointmentsDto){
        return ResponseEntity.ok(appointmentService.listAllAppointmentsBetweenDates(id,appointmentsDto));
    }

    @GetMapping // not tested
    public ResponseEntity<List<AppointmentsDto>> getAppointments(){
        return ResponseEntity.ok(appointmentService.findAll());
    }

    @DeleteMapping("/{id}") // not tested
    public ResponseEntity<Void> deleteAppointment(@PathVariable Integer id){
        appointmentService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
