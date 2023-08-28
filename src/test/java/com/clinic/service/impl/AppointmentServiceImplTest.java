package com.clinic.service.impl;

import com.clinic.domain.dto.AppointmentsDto;
import com.clinic.entity.Appointments;
import com.clinic.entity.DoctorSchedule;
import com.clinic.entity.Patient;
import com.clinic.entity.User;
import com.clinic.repository.AppointmentRepository;
import com.clinic.service.AppointmentService;
import com.clinic.service.DoctorScheduleService;
import com.clinic.service.PatientService;
import com.clinic.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class AppointmentServiceImplTest {

    @Autowired
    private AppointmentService appointmentService;

    @MockBean
    private AppointmentRepository appointmentRepository;
    @MockBean
    private PatientService patientService;
    @MockBean
    private DoctorScheduleService doctorScheduleService;
    @MockBean
    private UserService userService;

    @Test
    void createNewWithRegisteredPatient() {

        AppointmentsDto appointmentsDto = new AppointmentsDto();
        Integer patientId = 1;

        Patient patient = new Patient();
        when(patientService.findById(patientId)).thenReturn(patient);
        when(appointmentRepository.save(any(Appointments.class))).thenReturn(new Appointments());

        AppointmentsDto result = appointmentService.createNewWithRegisteredPatient(appointmentsDto, patientId);

        assertNotNull(result);

        verify(patientService).findById(patientId);
        verify(appointmentRepository).save(any(Appointments.class));
    }
    @Test
    void updateById() {

        Integer appointmentId = 1;
        AppointmentsDto appointmentsDto = new AppointmentsDto();

        DoctorSchedule doctorSchedule = new DoctorSchedule();
        Appointments appointment = new Appointments();
        when(appointmentService.findById(appointmentId)).thenReturn(appointment);
        when(doctorScheduleService.isAppointmentWithinDoctorSchedule(anyInt(), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(true);
        when(appointmentRepository.hasOverlappingAppointments(anyInt(), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(false);
        when(appointmentRepository.save(any(Appointments.class))).thenReturn(new Appointments());
        AppointmentsDto result = appointmentService.updateById(appointmentId, appointmentsDto);
        assertNotNull(result);
        verify(appointmentService).findById(appointmentId);
        verify(doctorScheduleService).isAppointmentWithinDoctorSchedule(anyInt(), any(LocalDateTime.class), any(LocalDateTime.class));
        verify(appointmentRepository).hasOverlappingAppointments(anyInt(), any(LocalDateTime.class), any(LocalDateTime.class));
        verify(appointmentRepository).save(any(Appointments.class));
    }


    @Test
    void listAllAppointmentsBetweenDates() {
        Integer userId = 1;
        AppointmentsDto appointmentsDto = new AppointmentsDto();

        DoctorSchedule doctorSchedule = new DoctorSchedule();
        when(userService.findById(userId)).thenReturn(new User());
        when(appointmentRepository.findAllByDoctorScheduleAndStartOfAppointmentBetween(any(DoctorSchedule.class), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(Collections.singletonList(new Appointments()));

        List<AppointmentsDto> result = appointmentService.listAllAppointmentsBetweenDates(userId, appointmentsDto);

        assertNotNull(result);
        assertFalse(result.isEmpty());

        verify(userService).findById(userId);
        verify(appointmentRepository).findAllByDoctorScheduleAndStartOfAppointmentBetween(any(DoctorSchedule.class), any(LocalDateTime.class), any(LocalDateTime.class));

    }
}
}