package com.clinic.service.impl;

import com.clinic.configuration.SecurityUtils;
import com.clinic.domain.dto.*;
import com.clinic.domain.exception.*;
import com.clinic.domain.mapper.DaysOffMapper;
import com.clinic.domain.mapper.DoctorScheduleMapper;
import com.clinic.domain.mapper.UserMapper;
import com.clinic.entity.*;
import com.clinic.repository.DaysOffRepository;
import com.clinic.repository.DepartmentRepository;
import com.clinic.repository.DoctorScheduleRepository;
import com.clinic.repository.UserRepository;
import com.clinic.service.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalTime;
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
    private final DepartmentRepository departmentRepository;
    private final DoctorScheduleService doctorScheduleService;
    private final DoctorScheduleRepository doctorScheduleRepository;
    private final DaysOffRepository daysOffRepository;




    @Transactional
    @Override // Works don't touch
    public UserDto registerDetails(@Valid RegisterForm form) {
        var user= new User();
        var scheduleOfDoctor = new DoctorSchedule(LocalTime.of(8,0),LocalTime.of(17,0));
        var daysOffForUser = new ArrayList<DaysOff>();
        user.setEmail(form.getEmail());
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        user.setFirstname(form.getFirstname());
        user.setLastname(form.getLastname());
        user.setRole(Role.fromValue("DOCTOR"));
        user.setSchedule(scheduleOfDoctor);
        user.setDaysOff(daysOffForUser);
        doctorScheduleRepository.save(scheduleOfDoctor);
        return toDto(userRepository.save(user));
    }

    @Transactional
    @Override
    public UserDto setDaysOffForUser(DaysOffDto dates, Integer userId){
        var user = findById(userId);
        if (user.getRole().equals(Role.ADMIN)){
            throw new WrongRoleException("This role cant have days off");
        }
        List<DaysOff> userDaysOff = user.getDaysOff();
        var daysOffToBeAdded = DaysOffMapper.toEntity(dates);
        if (userDaysOff.stream().anyMatch(daysOff -> daysOff.getDaysOff().equals(daysOffToBeAdded.getDaysOff()))) {
            throw new TimeOverlapException("User already has a day off on this date");
        }
        daysOffRepository.save(daysOffToBeAdded);
        userDaysOff.add(daysOffToBeAdded);
        userRepository.save(user);
        return toDto(user);
    }
    @Transactional
    @Override
    public UserDto deleteDayOffForUser(Integer userId, Integer dayOffId){
        var user = findById(userId);
        if (user.getRole().equals(Role.ADMIN)){
            throw new WrongRoleException("This role cant have days off");
        }
        List<DaysOff> userDaysOff = user.getDaysOff();
        var dayOff = daysOffRepository.findById(dayOffId).orElseThrow(()-> new ResourceNotFoundException(String
                .format("DayOff with id %s do not exist",dayOffId)));;
        if (!userDaysOff.contains(dayOff)){
            throw new ResourceNotFoundException("User doesnt have that day off");
        }else userDaysOff.remove(dayOff);
        daysOffRepository.delete(dayOff);
        userRepository.save(user);
        return  toDto(user);
    }

    @Transactional
    @Override // Works don't touch
    public UserDto registerDetailsForWorker(@Valid RegisterForm form) {
        var user= new User();
        user.setEmail(form.getEmail());
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        user.setFirstname(form.getFirstname());
        user.setLastname(form.getLastname());
        user.setRole(Role.fromValue("WORKER"));
        return toDto(userRepository.save(user));
    }
    @Transactional
    @Override // WORKS -- useri i loguar ndryshon passwordin e vet.
    public UserDto updatePassword(PasswordChanger passwordChanger){
        var auth = SecurityUtils.getLoggedUserId();
        var loggedUser = findById(auth);
        if (!passwordEncoder.matches(passwordChanger.getOldPassword(), loggedUser.getPassword())) {
            throw new PermissionNotAllowedException("Old password not correct");
        }
        if (passwordEncoder.matches(passwordChanger.getNewPassword(), loggedUser.getPassword())) {
            throw new PermissionNotAllowedException("New password can't be the same as the old password");
        }
        loggedUser.setPassword(passwordEncoder.encode(passwordChanger.getNewPassword()));

        return toDto(userRepository.save(loggedUser));
    }

    @Override
    public List<UserDto> findAllByFirstname(String name) {
        return userRepository.findAllByFirstnameContainingIgnoreCase(name).stream().map(UserMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<UserDto> findAllByLastname(String name) {
        return userRepository.findAllByLastnameContainingIgnoreCase(name).stream().map(UserMapper::toDto).collect(Collectors.toList()) ;
    }

    @Override
    @Transactional
    public UserDto changePassword(Integer userId, NewPasswordAdminOnly password){
        var user = findById(userId);
        if (passwordEncoder.matches(password.getPassword(), user.getPassword())) {
            throw new PermissionNotAllowedException("New password can't be the same as the old password");
        }
        user.setPassword(passwordEncoder.encode(password.getPassword()));
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
        user.setRole(Role.fromValue("ADMIN"));
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
        if (doctor.getRole() == Role.DOCTOR) { // mund ta bejm qe te krijimi userit
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
        if (doctorScheduleDto.getEndTime().isBefore(doctorScheduleDto.getStartTime())||doctorScheduleDto.getEndTime().equals(doctorScheduleDto.getStartTime())){
            throw new TimeOverlapException("Please put a correct date format.");
        }
        if (user.isDeleted()){
            throw new UserDeletedException("This action is not allowed as user is already deleted!");
        }
        if (!(user.getRole()== Role.DOCTOR)){
            throw new WrongRoleException(String.format("User with id %s is a not a doctor, and doesnt have a schedule",id));
        }
        if (!Objects.equals(id, loggedUser) && !(findById(loggedUser).getRole()== Role.ADMIN )){
            throw new WrongRoleException(String.format("User with id %s cannot change the schedule",loggedUser));
        }
        var schedule = user.getSchedule();
        List<Appointments> appointments = schedule.getAppointments(); // this part can be reworked with a JPA query.
        for (Appointments a: appointments) {
            if (doctorScheduleDto.getStartTime().isAfter(a.getStartOfAppointment().toLocalTime())||doctorScheduleDto.getStartTime().isAfter(a.getEndOfAppointment().toLocalTime())||
            doctorScheduleDto.getEndTime().isBefore(a.getStartOfAppointment().toLocalTime())||doctorScheduleDto.getEndTime().isBefore(a.getEndOfAppointment().toLocalTime())){
                throw new TimeOverlapException(("Schedule time update overlaps with some of the appointments, please cancel those before doing the change."));
            }
        }
        doctorScheduleService.updateSchedule(schedule.getId(),doctorScheduleDto);
        user.setSchedule(schedule);
        return toDto(user);
    }
    @Override //Works
    public DoctorScheduleDto getDoctorScheduleByDoctorId(Integer id){
        var doctor = findById(id);
        if (!(doctor.getRole()== Role.DOCTOR)){
            throw new WrongRoleException(String.format("User with id %s isn't a 'doctor', and doesnt have a schedule",id));
        }
        return DoctorScheduleMapper.toDto(doctor.getSchedule());
    }
    @Transactional
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

    @Override
   public List<UserDto> findDoctorWithMostAppointments(){
        return userRepository.findDoctorWithMostAppointments().stream().map(UserMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Scheduled(cron = "0 0 0 */30 * ?")
    public void deleteByDeletedTrue() {
        userRepository.deleteByDeletedTrue();
    }

    @Override
    public List <UserDto> doctorThatPatientVisitsTheMost(Integer patientId) {
        return userRepository.findDoctorWithMostVisits(patientId).stream().map(UserMapper::toDto).collect(Collectors.toList());//.orElseThrow(()-> new ResourceNotFoundException("Patient doesnt have any visits")));
    }


}
