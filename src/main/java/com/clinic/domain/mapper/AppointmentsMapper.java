package com.clinic.domain.mapper;

import com.clinic.domain.dto.AppointmentsDto;
import com.clinic.entity.Appointments;
import com.clinic.entity.Patient;

public class AppointmentsMapper {

    public static AppointmentsDto toDto (Appointments a){
        AppointmentsDto appointmentsDto = new AppointmentsDto();
        appointmentsDto.setId(a.getId());
        appointmentsDto.setStartOfAppointment(a.getStartOfAppointment());
        appointmentsDto.setEndOfAppointment(a.getEndOfAppointment());
        appointmentsDto.setPatientDto(a.getPatient().getName());
        return appointmentsDto;
    }
    public static Appointments toEntity(AppointmentsDto a){
        Appointments appointments = new Appointments();
        appointments.setId(a.getId());
        appointments.setStartOfAppointment(a.getStartOfAppointment());
        appointments.setEndOfAppointment(a.getEndOfAppointment());
        return appointments;
    }
    public static AppointmentsDto toDtoNoPatients (Appointments a){
        AppointmentsDto appointmentsDto = new AppointmentsDto();
        appointmentsDto.setId(a.getId());
        appointmentsDto.setStartOfAppointment(a.getStartOfAppointment());
        appointmentsDto.setEndOfAppointment(a.getEndOfAppointment());
        return appointmentsDto;
    }
    public static Appointments toEntityNoPatients(AppointmentsDto a){
        Appointments appointments = new Appointments();
        appointments.setStartOfAppointment(a.getStartOfAppointment());
        appointments.setEndOfAppointment(a.getEndOfAppointment());
        return appointments;
    }
}
