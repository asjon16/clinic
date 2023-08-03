package com.clinic.service;

import com.clinic.domain.dto.AppointmentsDto;
import com.clinic.domain.dto.PatientDto;
import com.clinic.domain.dto.RegisterForm;
import com.clinic.domain.dto.UserDto;
import com.clinic.entity.Appointments;
import com.clinic.entity.Patient;
import com.clinic.entity.User;
import jakarta.validation.Valid;

import java.util.List;

public interface AppointmentService {

    AppointmentsDto createNoPatients(@Valid AppointmentsDto appointment);
    AppointmentsDto create(Integer id, PatientDto patient);

    AppointmentsDto createNewWithRegisteredPatient(AppointmentsDto appointmentsDto, Integer patientId);

    /*  AppointmentsDto update(Integer id,@Valid AppointmentsDto appointment);*/
    Appointments findById(Integer id);
    List<AppointmentsDto> findAll();

    void deleteById(Integer id);
}

