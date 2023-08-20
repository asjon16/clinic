package com.clinic.service;

import com.clinic.domain.dto.*;
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

    List<AppointmentsDto>listAllAppointmentsBetweenDates(Integer id, AppointmentsDto appointmentsDto);
    AppointmentsDto assignAnAppointment(Integer doctorId, AppointmentsDto appointmentsDto, Integer patientId);

    void deleteById(Integer id);

}

