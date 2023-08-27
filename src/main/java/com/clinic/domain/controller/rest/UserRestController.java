package com.clinic.domain.controller.rest;

import com.clinic.domain.dto.*;
import com.clinic.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;


import static com.clinic.domain.mapper.UserMapper.toDto;
import static com.clinic.domain.mapper.UserMapper.toEntity;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;

    @PreAuthorize("hasAnyRole('ADMIN','WORKER')")
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Integer id, @RequestBody UserDto u){
        return ResponseEntity.ok(userService.update(id,u));
    }
    @GetMapping("/schedule/{id}") // works keep working on it
    public ResponseEntity<DoctorScheduleDto> getScheduleOfDoctorWithDoctorId(@PathVariable Integer id){
        return ResponseEntity.ok((userService.getDoctorScheduleByDoctorId(id)));
    }
    @PreAuthorize("hasAnyRole('ADMIN','WORKER')")
    @PutMapping("/doctor/{id}") // Works don't touch
    public ResponseEntity<UserDto> assignDoctorToDepartment(@PathVariable Integer id, @RequestParam Integer d){
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

    @GetMapping //Works don't touch
    public ResponseEntity<List<UserDto>> getUsers(){
        return ResponseEntity.ok(userService.findAll());
    }
    @GetMapping ("/mostappointments")//Works don't touch
    public ResponseEntity<List<UserDto>> findDoctorWithMostAppointments(){
        return ResponseEntity.ok(userService.findDoctorWithMostAppointments());
    }

    @GetMapping ("/firstname")//Works don't touch
    public ResponseEntity<List<UserDto>> findUserWithFirstNAme(@RequestParam String name){
        return ResponseEntity.ok(userService.findAllByFirstname(name));
    }

    @GetMapping ("/lastname")//Works don't touch
    public ResponseEntity<List<UserDto>> findUserWithLastName(@RequestParam String name){
        return ResponseEntity.ok(userService.findAllByLastname(name));
    }
    @GetMapping ("/mostvisited")//Works don't touch
    public ResponseEntity<List<UserDto>> findDoctorThatPatientVisitMostly(@RequestParam Integer patientId){
        return ResponseEntity.ok(userService.doctorThatPatientVisitsTheMost(patientId));
    }

    @PreAuthorize("hasAnyRole('ADMIN','WORKER')")
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
    @PostMapping("/dayoff/{id}")
    public ResponseEntity<UserDto> addDayOffForDoctor(@RequestBody DaysOffDto daysOffDto,@PathVariable Integer id){
        return ResponseEntity.ok(userService.setDaysOffForUser(daysOffDto,id));
    }

    @DeleteMapping("/dayoff/{id}")
    public ResponseEntity<UserDto> removeDayOffForDoctor(@PathVariable Integer id,@RequestParam Integer dayOffId){
        return ResponseEntity.ok(userService.deleteDayOffForUser(id,dayOffId));
    }
    @PutMapping("/password")
    public ResponseEntity<UserDto>updatePassword(@RequestBody @Valid PasswordChanger passwordChanger){
        return ResponseEntity.ok(userService.updatePassword(passwordChanger));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/password/{id}")
    public ResponseEntity<UserDto>changePassword(@PathVariable Integer id,@RequestBody @Valid NewPasswordAdminOnly password){
        return ResponseEntity.ok(userService.changePassword(id,password));
    }


}
