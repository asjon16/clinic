package com.clinic.service.impl;

import com.clinic.domain.dto.*;
import com.clinic.domain.exception.ResourceNotFoundException;
import com.clinic.domain.exception.UserDeletedException;
import com.clinic.domain.exception.WrongRoleException;
import com.clinic.domain.mapper.DoctorScheduleMapper;
import com.clinic.domain.mapper.UserMapper;
import com.clinic.entity.*;
import com.clinic.repository.DepartmentRepository;
import com.clinic.repository.DoctorScheduleRepository;
import com.clinic.repository.UserRepository;
import com.clinic.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import static com.clinic.domain.mapper.UserMapper.toDto;
import static com.clinic.domain.mapper.UserMapper.toEntity;

@RequiredArgsConstructor
@Validated
@Service
public class UserServiceImplements implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final DepartmentService departmentService;
    private final PatientService patientService;
    private final AppointmentService appointmentService;
    private final DepartmentRepository departmentRepository;
    private final DoctorScheduleService doctorScheduleService;
    private final DoctorScheduleRepository doctorScheduleRepository;

    private LocalDateTime now = LocalDateTime.now();


    @Override // Works don't touch
    public UserDto registerDetails(@Valid RegisterForm form) {
        var user= new User();
        user.setEmail(form.getEmail());
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        user.setFirstname(form.getFirstname());
        user.setLastname(form.getLastname());
        user.setRole(UserRole.fromValue(form.getRole()));
        return toDto(userRepository.save(user));
    }

    @Override // Works but don't use it
    public UserDto create(@Valid UserDto user) {
        var result = userRepository.save(toEntity(user));
        return toDto(result);
    }

    @Override // Works don't touch
    public UserDto assignDoctorToDepartment(Integer doctorId, Integer departmentId) {
        var doctor = findById(doctorId);
        var department = departmentService.findById(departmentId);
        if (doctor.isDeleted()) {
            throw new UserDeletedException(String
                    .format("The user with id %s is deleted from the system and cannot be assigned a department", doctorId));
        }
        if (doctor.getRole() == UserRole.DOCTOR) {
                doctor.setDoctorDepartment(department);
                userRepository.save(doctor);
            } else {
                throw new WrongRoleException(String
                        .format("The user with id %s is not a doctor, you cannot assign a department", doctorId));
            }
        return toDto(doctor);
    }
    @Override // Works don't touch
    public List<UserDto> findAllDoctorsByDepartmentId(Integer departmentId) {
        return userRepository.findAllDoctorsByDepartmentId(departmentId)
                .stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }
    @Override // to be reviewed
    public UserDto assignScheduleToDoctor(Integer id, DoctorScheduleDto doctorScheduleDto){
        var result = findById(id);
        DoctorSchedule schedule = result.getSchedule();
        if (schedule == null) {
            schedule = new DoctorSchedule();
        }
        var newSchedule = doctorScheduleService.updateSchedule(schedule.getId(),doctorScheduleDto);
        var omg = DoctorScheduleMapper.toEntity(newSchedule);
        omg.setDoctor(result);
        doctorScheduleRepository.save(omg);
        result.setSchedule(omg);
        userRepository.save(result);
        return toDto(result);
    }
    public UserDto assignAnAppointment(Integer doctorId, Integer appointmentId){
        var doctor = findById(doctorId);
        var doctorSchedule = doctor.getSchedule();
        if (doctorSchedule==null){
            doctorSchedule=new DoctorSchedule();
        }
        var appointment = appointmentService.findById(appointmentId);
       List<Appointments> appointments= doctorSchedule.getAppointments();
       if (appointments == null){
           appointments=new ArrayList<>();
       }
       appointments.add(appointment);

       return toDto(doctor);


    }

    @Override // Doesnt work, it wont change the password
    public UserDto update(Integer id, @Valid UserDto userDto) {
        var user = findById(id);
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setPassword(user.getPassword());
        userRepository.save(user);
        return toDto(user);
    }

    @Override // Works don't touch
    public User findById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(String
                        .format("User with id %s do not exist",id)));
    }

    @Override // Works don't touch
    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(UserMapper::toDto).collect(Collectors.toList());
    }

    @Override // Works don't touch
    public void deleteById(Integer id) {
        var toDelete = findById(id);
        toDelete.setDeleted(true);
        userRepository.save(toDelete);
    }


}
