package com.clinic.service.impl;

import com.clinic.domain.dto.AppointmentsDto;
import com.clinic.domain.dto.PatientDto;
import com.clinic.domain.exception.ResourceAlreadyExistsException;
import com.clinic.domain.exception.ResourceNotFoundException;
import com.clinic.domain.mapper.AppointmentsMapper;
import com.clinic.domain.mapper.PatientMapper;
import com.clinic.entity.Appointments;
import com.clinic.repository.AppointmentRepository;
import com.clinic.repository.PatientRepository;
import com.clinic.service.AppointmentService;
import com.clinic.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
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


    @Override
    public AppointmentsDto createNoPatients(AppointmentsDto appointment) {
        var result = AppointmentsMapper.toEntityNoPatients(appointment);
        appointmentRepository.save(result);
        return toDtoNoPatients(result);
    }

    @Override
    public AppointmentsDto create(Integer id, PatientDto patient) {
       var result = findById(id);
         patientService.create(patient);
         result.setPatient(PatientMapper.toEntity(patient));
         appointmentRepository.save(result);
         return toDto(result);
    }
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
    public Appointments findById(Integer id) {
        return  appointmentRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(String
                .format("Appointment with id %s do not exist",id)));
    }
    @Override // works
    public AppointmentsDto updateById(Integer id,AppointmentsDto appointmentsDto){
        var appointment = findById(id);
        appointment.setStartOfAppointment(appointmentsDto.getStartOfAppointment());
        appointment.setEndOfAppointment(appointmentsDto.getEndOfAppointment());
        appointmentRepository.save(appointment);
        return toDto(appointment);
    }

    @Override // Works needs more testing
    public List<Appointments> findAllByPatient_idAndStartOfAppointmentAndEndOfAppointment(Integer patientId, LocalDateTime start, LocalDateTime end) {
       var patient = patientService.findById(patientId);
       List<Appointments> patientAppointment = patient.getAppointment();
       if (patientAppointment.stream().anyMatch(p->p.getStartOfAppointment().isEqual(start))){ // if appointment start at the same time, means its a dupe
           throw new ResourceAlreadyExistsException("Patient already has an other appointment in this time");
       }else {
           var newAppointment = new Appointments();
           newAppointment.setStartOfAppointment(start);
           newAppointment.setEndOfAppointment(end);
           createNewWithRegisteredPatient(AppointmentsMapper.toDtoNoPatients(newAppointment),patientId);
           patientAppointment.add(newAppointment);

       }
       return patientAppointment;

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
