package com.clinic.service.impl;

import com.clinic.domain.dto.AppointmentsDto;
import com.clinic.domain.dto.DoctorScheduleDto;
import com.clinic.domain.dto.PatientDto;
import com.clinic.domain.exception.AppointmentAlreadyAssignedException;
import com.clinic.domain.exception.ResourceAlreadyExistsException;
import com.clinic.domain.exception.ResourceNotFoundException;
import com.clinic.domain.exception.TimeOverlapException;
import com.clinic.domain.mapper.AppointmentsMapper;
import com.clinic.domain.mapper.PatientMapper;
import com.clinic.entity.Appointments;
import com.clinic.repository.AppointmentRepository;
import com.clinic.repository.DoctorScheduleRepository;
import com.clinic.repository.PatientRepository;
import com.clinic.repository.UserRepository;
import com.clinic.service.AppointmentService;
import com.clinic.service.DoctorScheduleService;
import com.clinic.service.PatientService;
import com.clinic.service.UserService;
import groovy.lang.Lazy;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.clinic.domain.mapper.AppointmentsMapper.*;

@RequiredArgsConstructor
@Validated
@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    private final PatientService patientService;
    private final PatientRepository patientRepository;
    private final DoctorScheduleService doctorScheduleService;
    private final DoctorScheduleRepository doctorScheduleRepository;
    private final UserService userService;
    private final UserRepository userRepository;


    @Override // works
    public AppointmentsDto createNewWithRegisteredPatient(AppointmentsDto appointmentsDto, Integer patientId) {
       var appointment = toEntityNoPatients(appointmentsDto);
        var patient = patientService.findById(patientId);
        appointment.setPatient(patient);
        appointmentRepository.save(appointment);
        patientRepository.save(patient);
        return toDto(appointment);
    }
    @Override
    @Transactional // Krijon nje appointment te schedule i doktorit qe fusim me ID me pacientin qe kemi regjistruar
    public AppointmentsDto assignAnAppointment(Integer doctorId, AppointmentsDto appointmentsDto, Integer patientId){

        if (!appointmentsDto.getStartOfAppointment().plusHours(1).isEqual(appointmentsDto.getEndOfAppointment())){
            throw new TimeOverlapException("The times given are not correct, please double check");
        }
        var doctor = userService.findById(doctorId);
        var doctorSchedule = doctor.getSchedule();
        var patient = patientService.findById(patientId);
        var appointment = createNewWithRegisteredPatient(appointmentsDto,patientId);
        var appointmentButEntity= toEntity(appointment);
        appointmentButEntity.setPatient(patient);
        appointmentButEntity.setDoctorSchedule(doctorSchedule);
        List<Appointments> appointments= doctorSchedule.getAppointments();
        if (appointments == null){
            appointments=new ArrayList<>();
        }
        if (appointmentRepository.hasOverlappingAppointments(doctorSchedule.getId(),appointmentsDto.getStartOfAppointment(),appointmentsDto.getEndOfAppointment())){
            throw new TimeOverlapException("Appointment overlap with an other appointment, please select an other date.");
        }
        if (!doctorScheduleService.isAppointmentWithinDoctorSchedule(doctorSchedule.getId(),appointmentsDto.getStartOfAppointment(),appointmentsDto.getEndOfAppointment())){
            throw new TimeOverlapException("That appointment is outside the doctor's hours");
        }
        appointmentRepository.save(appointmentButEntity);
        appointments.add(appointmentButEntity);
        doctorScheduleRepository.save(doctorSchedule);
        userRepository.save(doctor);
        return toDto(appointmentButEntity);

    }



    @Override
    public Appointments findById(Integer id) {
        return  appointmentRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(String
                .format("Appointment with id %s do not exist",id)));
    }
    private boolean isTimeOverlap(LocalDateTime start1, LocalDateTime end1, LocalDateTime start2, LocalDateTime end2) {
        return start1.isBefore(end2) && end1.isAfter(start2);
    }
    @Transactional
    @Override // WORKS
    public AppointmentsDto updateById(Integer id,AppointmentsDto appointmentsDto){
        if (appointmentsDto.getEndOfAppointment().isEqual(appointmentsDto.getStartOfAppointment())||
                (appointmentsDto.getEndOfAppointment().isBefore(appointmentsDto.getStartOfAppointment()))){
            throw new TimeOverlapException("The times given are not correct, please double check");
        }
        var appointment = findById(id);
        var schedule = appointment.getDoctorSchedule();
        if (appointmentRepository.hasOverlappingAppointments(schedule.getId(),appointmentsDto.getStartOfAppointment(),appointmentsDto.getEndOfAppointment())){
            throw new TimeOverlapException("That time slot is taken by another appointment, please try another hour.");
        }
        if (!doctorScheduleService.isAppointmentWithinDoctorSchedule(schedule.getId(),appointmentsDto.getStartOfAppointment(),appointmentsDto.getEndOfAppointment())){
            throw new TimeOverlapException("That appointment is outside the doctor's hours");
        }
        appointment.setStartOfAppointment(appointmentsDto.getStartOfAppointment());
        appointment.setEndOfAppointment(appointmentsDto.getEndOfAppointment());
        appointmentRepository.save(appointment);
        return toDto(appointment);
    }




    @Override //WORKS
    public List<AppointmentsDto> findAllAppointmentByPatient_id(Integer patientId) {
        List<AppointmentsDto>myList= appointmentRepository.findAllAppointmentByPatient_id(patientId).stream().map(AppointmentsMapper::toDto).collect(Collectors.toList());
        if (myList.isEmpty()){
            throw new ResourceNotFoundException(String.format("Patient with id %s doesnt have any appointments",patientId));
        }
        return myList;
    }

    @Override // Wokrs
    public List<AppointmentsDto> listAllAppointmentsBetweenDates(Integer id, AppointmentsDto appointmentsDto) {
        var user = userService.findById(id);
        var schedule = user.getSchedule();
        return appointmentRepository.findAllByDoctorScheduleAndStartOfAppointmentBetween(schedule,appointmentsDto.getStartOfAppointment(),appointmentsDto.getEndOfAppointment())
                .stream().map(AppointmentsMapper::toDto).collect(Collectors.toList());
    }


    @Override
    public List<AppointmentsDto> findAll() {
       return appointmentRepository.findAll().stream().map(AppointmentsMapper::toDto).collect(Collectors.toList());
    }



    @Override
    public void deleteById(Integer id) {
       var toDelete= findById(id);
       appointmentRepository.delete(toDelete);
    }
}
