package com.clinic.service;

import com.clinic.domain.dto.AppointmentsDto;
import com.clinic.domain.dto.PatientDto;
import com.clinic.domain.dto.RegisterForm;
import com.clinic.domain.dto.UserDto;
import com.clinic.entity.Appointments;
import com.clinic.entity.Patient;
import com.clinic.entity.User;
import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentService {


    AppointmentsDto createNewWithRegisteredPatient(AppointmentsDto appointmentsDto, Integer patientId);

    Appointments findById(Integer id);
    List<AppointmentsDto> findAll();
    AppointmentsDto updateById(Integer id,AppointmentsDto appointmentsDto);

    List<AppointmentsDto> findAllAppointmentByPatient_id(Integer patientId);

    void deleteById(Integer id);

}

