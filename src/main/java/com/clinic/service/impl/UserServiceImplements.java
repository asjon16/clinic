package com.clinic.service.impl;

import com.clinic.configuration.SecurityUtils;
import com.clinic.domain.dto.*;
import com.clinic.domain.exception.*;
import com.clinic.domain.mapper.AppointmentsMapper;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


import static com.clinic.domain.mapper.UserMapper.toDto;

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
        user.setRole(UserRole.fromValue("DOCTOR"));
        user.setSchedule(scheduleOfDoctor);
        doctorScheduleRepository.save(scheduleOfDoctor);
        return toDto(userRepository.save(user));
    }

    @Transactional
    @Override // Works don't touch
    public UserDto registerDetailsForWorker(@Valid RegisterForm form) {
        var user= new User();
        user.setEmail(form.getEmail());
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        user.setFirstname(form.getFirstname());
        user.setLastname(form.getLastname());
        user.setRole(UserRole.fromValue("WORKER"));
        return toDto(userRepository.save(user));
    }
    @Transactional
    @Override // Works don't touch
    public UserDto registerDetailsForAdmin(@Valid RegisterForm form) {
        var user= new User();
        user.setEmail(form.getEmail());
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        user.setFirstname(form.getFirstname());
        user.setLastname(form.getLastname());
        user.setRole(UserRole.fromValue("ADMIN"));
        return toDto(userRepository.save(user));
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

    @Transactional
    @Override // ndryshon orarin e punes te doktorit -- works dont touch !!!!
    public UserDto updateDoctorSchedule(Integer id, DoctorScheduleDto doctorScheduleDto) {
        var loggedUser = SecurityUtils.getLoggedUserId();
        var user = findById(id);
        if (user.getRole()==UserRole.WORKER){
            throw new WrongRoleException(String.format("User with id %s is a 'worker', and doesnt have a schedule",id));
        }
        if (!Objects.equals(id, loggedUser) && !(findById(loggedUser).getRole()==UserRole.ADMIN)){
            throw new WrongRoleException(String.format("User with id %s cannot change the schedule",loggedUser));
        }
        var schedule = user.getSchedule();
        List<Appointments> appointments = schedule.getAppointments();
        for (Appointments a: appointments) {
            if (doctorScheduleDto.getStartTime().isAfter(a.getStartOfAppointment())||doctorScheduleDto.getStartTime().isAfter(a.getEndOfAppointment())||
            doctorScheduleDto.getEndTime().isBefore(a.getStartOfAppointment())||doctorScheduleDto.getEndTime().isBefore(a.getEndOfAppointment())){
                throw new TimeOverlapException(("Schedule time update overlaps with some of the appointments, please cancel those before doing the change."));
            }
        }
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

    //WORKS DONT TOUCH!
    @Override
    @Transactional // Krijon nje appointment te schedule i doktorit qe fusim me ID me pacientin qe kemi regjistruar
    public UserDto assignAnAppointment(Integer doctorId, AppointmentsDto appointmentsDto, Integer patientId){
        var doctor = findById(doctorId);
        var doctorSchedule = doctor.getSchedule();
        var patient = patientService.findById(patientId);
        var appointment = appointmentService.createNewWithRegisteredPatient(appointmentsDto,patientId);
        var appointmentButEntity= AppointmentsMapper.toEntity(appointment);
        appointmentButEntity.setPatient(patient);
        appointmentButEntity.setDoctorSchedule(doctorSchedule);
       List<Appointments> appointments= doctorSchedule.getAppointments();
       if (appointments == null){
           appointments=new ArrayList<>();
       }
        for (Appointments existingAppointment : appointments) {
            if (isTimeOverlap(appointment.getStartOfAppointment(), appointment.getEndOfAppointment(),
                    existingAppointment.getStartOfAppointment(), existingAppointment.getEndOfAppointment())) {
                throw new TimeOverlapException("That time slot is taken by another appointment, please try another hour.");
            } else if (existingAppointment.getId().equals(appointmentButEntity.getId())) {
                throw new AppointmentAlreadyAssignedException(String.format("Appointment with id %s already assigned to the doctor.", appointmentsDto));
            }
        }
        if (appointment.getStartOfAppointment().isBefore(doctorSchedule.getStartTime())||appointment.getStartOfAppointment().isAfter(doctorSchedule.getEndTime())
        ||appointment.getEndOfAppointment().isBefore(doctorSchedule.getStartTime())||appointment.getEndOfAppointment().isAfter(doctorSchedule.getEndTime())){
            throw new TimeOverlapException("That appointment is outside the doctor's hours");
        }
        appointmentRepository.save(appointmentButEntity);
       appointments.add(appointmentButEntity);
       doctorScheduleRepository.save(doctorSchedule);
       userRepository.save(doctor);
       return toDto(doctor);

    }

    @Override
    @Transactional // Krijon nje appointment te schedule i doktorit qe fusim me ID me pacientin qe kemi regjistruar
    public UserDto testForUpdate(Integer doctorId, AppointmentsDto appointmentsDto, Integer patientId){
        if (appointmentsDto.getEndOfAppointment().isEqual(appointmentsDto.getStartOfAppointment())||
                (appointmentsDto.getEndOfAppointment().isBefore(appointmentsDto.getStartOfAppointment()))){
            throw new TimeOverlapException("The times given are not correct, please double check");
        }
        var doctor = findById(doctorId);
        var doctorSchedule = doctor.getSchedule();
        var patient = patientService.findById(patientId);
        var appointment = appointmentService.createNewWithRegisteredPatient(appointmentsDto,patientId);
        var appointmentButEntity= AppointmentsMapper.toEntity(appointment);
        appointmentButEntity.setPatient(patient);
        appointmentButEntity.setDoctorSchedule(doctorSchedule);
        List<Appointments> appointments= doctorSchedule.getAppointments();
        if (appointments == null){
            appointments=new ArrayList<>();
        }
        if (appointmentRepository.hasOverlappingAppointments(doctorSchedule.getId(),appointmentsDto.getStartOfAppointment(),appointmentsDto.getEndOfAppointment())){
            throw new TimeOverlapException("That time slot is taken by another appointment, please try another hour.");
        }
        if (!doctorScheduleRepository.isAppointmentWithinDoctorSchedule(doctorSchedule.getId(),appointmentsDto.getStartOfAppointment(),appointmentsDto.getEndOfAppointment())){
            throw new TimeOverlapException("That appointment is outside the doctor's hours");
        }
        appointmentRepository.save(appointmentButEntity);
        appointments.add(appointmentButEntity);
        doctorScheduleRepository.save(doctorSchedule);
        userRepository.save(doctor);
        return toDto(doctor);

    }


    @Override // to update only name and last name
    public UserDto update(Integer id, @Valid UserDto userDto) {
        var user = findById(id);
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
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
        var user = findById(userId);
        var scheduleOfUser = user.getSchedule();
        if ((scheduleOfUser.getAppointments()==null)){
            throw new ResourceNotFoundException(String.format("User with id %s doesn't have any appointments on that day",userId));
        }else return toDto(userRepository.findUserWithAppointmentsForDate(userId, date, nextDate));
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
