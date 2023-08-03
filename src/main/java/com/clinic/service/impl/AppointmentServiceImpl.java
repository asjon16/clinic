package com.clinic.service.impl;

import com.clinic.domain.dto.AppointmentsDto;
import com.clinic.domain.dto.PatientDto;
import com.clinic.domain.exception.ResourceNotFoundException;
import com.clinic.domain.mapper.AppointmentsMapper;
import com.clinic.domain.mapper.PatientMapper;
import com.clinic.entity.Appointments;
import com.clinic.repository.AppointmentRepository;
import com.clinic.service.AppointmentService;
import com.clinic.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

import static com.clinic.domain.mapper.AppointmentsMapper.*;

@RequiredArgsConstructor
@Validated
@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientService patientService;


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
    @Override
    public AppointmentsDto createNewWithRegisteredPatient(AppointmentsDto appointmentsDto, Integer patientId) {
       var appointment = toEntityNoPatients(appointmentsDto);
        var patient = patientService.findById(patientId);
        appointment.setPatient(patient);
        appointmentRepository.save(appointment);
        return toDto(appointment);
    }


    /*@Override
    public AppointmentsDto update(Integer id, AppointmentsDto appointment) {
        var result = findById(id);
        result.setId(appointment.getId());
        result.setStartOfAppointment(appointment.getStartOfAppointment());
        result.setEndOfAppointment(appointment.getEndOfAppointment());
        Patient patient = new Patient();
        PatientMapper.toEntity(appointment.getPatientDto());
        result.setPatient(PatientMapper.toEntity(appointment.getPatientDto()));
        appointmentRepository.save(result);
        return toDto(result);
    }*/

    @Override
    public Appointments findById(Integer id) {
        return  appointmentRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(String
                .format("Appointment with id %s do not exist",id)));
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
