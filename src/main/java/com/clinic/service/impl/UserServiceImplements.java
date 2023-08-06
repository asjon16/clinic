package com.clinic.service.impl;

import com.clinic.domain.dto.*;
import com.clinic.domain.exception.*;
import com.clinic.domain.mapper.DoctorScheduleMapper;
import com.clinic.domain.mapper.UserMapper;
import com.clinic.entity.*;
import com.clinic.repository.AppointmentRepository;
import com.clinic.repository.DepartmentRepository;
import com.clinic.repository.DoctorScheduleRepository;
import com.clinic.repository.UserRepository;
import com.clinic.service.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
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
    private final AppointmentRepository appointmentRepository;

    private LocalDateTime now = LocalDateTime.now();

    @Transactional
    @Override // Works don't touch
    public UserDto registerDetails(@Valid RegisterForm form) {
        var user= new User();
        var scheduleOfDoctor = new DoctorSchedule(LocalDateTime.now(),LocalDateTime.now().plusHours(8));
        user.setEmail(form.getEmail());
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        user.setFirstname(form.getFirstname());
        user.setLastname(form.getLastname());
        user.setRole(UserRole.fromValue(form.getRole()));
        if (user.getRole().equals(UserRole.DOCTOR)){
            user.setSchedule(scheduleOfDoctor);
            doctorScheduleRepository.save(scheduleOfDoctor);
        }
        return toDto(userRepository.save(user));
    }

    @Override // Works but don't use it -- register details does the job.
    public UserDto create(@Valid UserDto user) {
        var result = userRepository.save(toEntity(user));
        return toDto(result);
    }
    @Transactional
    @Override // Works don't touch - works - might update
    public UserDto assignDoctorToDepartment(Integer doctorId, Integer departmentId) {
        var doctor = findById(doctorId);
        var department = departmentService.findById(departmentId);
        if (doctor.isDeleted()) {
            throw new UserDeletedException(String
                    .format("The user with id %s is deleted from the system and cannot be assigned a department", doctorId));
        }
        if (doctor.getRole() == UserRole.DOCTOR) { // mund ta bejm qe te krijimi userit
                doctor.setDoctorDepartment(department);
                userRepository.save(doctor);
                departmentRepository.save(department);
            } else {
                throw new WrongRoleException(String
                        .format("The user with id %s is not a doctor, you cannot assign a department", doctorId));
            }
        return toDto(doctor);
    }
    @Override // Works don't touch
    public List<UserDto> findAllDoctorsByDepartmentId(Integer departmentId) {
        List<User> doctors = userRepository.findAllDoctorsByDepartmentId(departmentId);
        if (doctors.isEmpty()){
            throw new ResourceNotFoundException(String.format("There are no doctors assigned to the department id %s",departmentId));
        }
        return doctors.stream().map(UserMapper::toDto).collect(Collectors.toList());
    }

    @Override // ndryshon orarin e punes te doktorit -- works dont touch !!!!
    public UserDto updateDoctorSchedule(Integer id, DoctorScheduleDto doctorScheduleDto) {
        var user = findById(id);
        if (user.getRole()==UserRole.WORKER){
            throw new WrongRoleException(String.format("User with id %s is a 'worker', and doesnt have a schedule",id));
        }
        var schedule = user.getSchedule();
        doctorScheduleService.updateSchedule(schedule.getId(),doctorScheduleDto);
        user.setSchedule(schedule);
        return toDto(user);
    }
    @Override
    public DoctorScheduleDto getDoctorScheduleByDoctorId(Integer id){
        var doctor = findById(id);
        if (doctor.getRole()==UserRole.WORKER){
            throw new WrongRoleException(String.format("User with id %s is a 'worker', and doesnt have a schedule",id));
        }
        return DoctorScheduleMapper.toDto(doctor.getSchedule());
    }
    private boolean isTimeOverlap(LocalDateTime start1, LocalDateTime end1, LocalDateTime start2, LocalDateTime end2) {
        return start1.isBefore(end2) && end1.isAfter(start2);
    }
    //transactional
    @Override
    @Transactional
    public UserDto assignAnAppointment(Integer doctorId, Integer appointmentId){
        var doctor = findById(doctorId);
        var doctorSchedule = doctor.getSchedule();
        var appointment = appointmentService.findById(appointmentId);
        appointment.setDoctorSchedule(doctorSchedule);
       List<Appointments> appointments= doctorSchedule.getAppointments();
       if (appointments == null){
           appointments=new ArrayList<>();
       }
        for (Appointments existingAppointment : appointments) {
            if (isTimeOverlap(appointment.getStartOfAppointment(), appointment.getEndOfAppointment(),
                    existingAppointment.getStartOfAppointment(), existingAppointment.getEndOfAppointment())) {
                throw new TimeOverlapException("That time slot is taken by another appointment, please try another hour.");
            } else if (existingAppointment.getId().equals(appointmentId)) {
                throw new AppointmentAlreadyAssignedException(String.format("Appointment with id %s already assigned to the doctor.", appointmentId));
            }
        }
       appointments.add(appointment);
       doctorScheduleRepository.save(doctorSchedule);
       userRepository.save(doctor);
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
    @Override
    public UserDto findUserWithAppointmentsForDate(Integer userId, LocalDateTime date) {
        LocalDateTime nextDate = date.plusDays(1);

        return toDto(userRepository.findUserWithAppointmentsForDate(userId, date, nextDate));
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
