package com.clinic.domain.controller.rest;

import com.clinic.domain.dto.DepartmentsDto;
import com.clinic.domain.dto.DoctorScheduleDto;
import com.clinic.domain.dto.UserDto;
import com.clinic.entity.Appointments;
import com.clinic.entity.DoctorSchedule;
import com.clinic.entity.User;
import com.clinic.service.DoctorScheduleService;
import com.clinic.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


import static com.clinic.domain.mapper.UserMapper.toDto;
import static com.clinic.domain.mapper.UserMapper.toEntity;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;


    @PostMapping("/create") // works but don't use.
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto u){
        return ResponseEntity.ok(userService.create(u));
    }

    @PutMapping("/{id}") //Doesn't work, wont change password
    public ResponseEntity<UserDto> updateUser(@PathVariable Integer id, @RequestBody UserDto u){
        return ResponseEntity.ok(userService.update(id,u));
    }
    @PutMapping("/doctor/schedule/{doctorId}") // Testing
    public ResponseEntity<UserDto> addAppointmentToDoctorSchedule(@PathVariable Integer doctorId, @RequestBody Integer appointmentId){
        return ResponseEntity.ok(userService.assignAnAppointment(doctorId,appointmentId));
    }
    @PutMapping("/doctor/{id}") // Works don't touch
    public ResponseEntity<UserDto> assignDoctorToDepartment(@PathVariable Integer id, @RequestBody Integer d){
        return ResponseEntity.ok(userService.assignDoctorToDepartment(id,d));
    }

    @GetMapping("/{id}") // Works don't touch
    public ResponseEntity<UserDto> getUserById(@PathVariable Integer id){
        return ResponseEntity.ok(toDto(userService.findById(id)));
    }
    @GetMapping("/department/{departmentId}") // works don't touch
    public ResponseEntity<List<UserDto>> getUsersOfDepartment(@PathVariable Integer departmentId){
        return ResponseEntity.ok((userService.findAllDoctorsByDepartmentId(departmentId)));
    }

    @GetMapping("/{id}/appointments")
    public ResponseEntity<UserDto> getUserWithAppointmentsForDate(
            @PathVariable Integer id,
            @RequestParam("date") LocalDateTime date) {
        UserDto user = userService.findUserWithAppointmentsForDate(id, date);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/schedule/{id}") // works keep working on it
    public ResponseEntity<DoctorScheduleDto> getScheduleOfDoctorWithDoctorId(@PathVariable Integer id){
        return ResponseEntity.ok((userService.getDoctorScheduleByDoctorId(id)));
    }


    @GetMapping //Works don't touch
    public ResponseEntity<List<UserDto>> getUsers(){
        return ResponseEntity.ok(userService.findAll());
    }

    @DeleteMapping("/{id}") // Works don't touch
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id){
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/assign/{id}")
    public ResponseEntity<UserDto>assignScheduleToDoctor
            (@PathVariable Integer id,@RequestBody DoctorScheduleDto doctorScheduleDto){

        return ResponseEntity.ok(userService.updateDoctorSchedule(id,doctorScheduleDto));


    }


}
