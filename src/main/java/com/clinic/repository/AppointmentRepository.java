package com.clinic.repository;

import com.clinic.entity.Appointments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointments,Integer> {
    List<Appointments>findAllByPatient_idAndStartOfAppointmentAndEndOfAppointment (Integer patientId, LocalDateTime start, LocalDateTime end);

}
