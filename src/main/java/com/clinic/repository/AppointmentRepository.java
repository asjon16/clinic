package com.clinic.repository;

import com.clinic.entity.Appointments;
import com.clinic.entity.DoctorSchedule;
import com.clinic.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointments,Integer> {

    List<Appointments> findAllAppointmentByPatient_id(Integer patientId);

    @Query("SELECT COUNT(a) > 0 FROM Appointments a " +
            "WHERE a.doctorSchedule.id = :scheduleId " +
            "AND ((a.startOfAppointment < :endTime AND a.endOfAppointment > :startTime) " +
            "OR (a.startOfAppointment > :startTime AND a.startOfAppointment < :endTime))")
    boolean hasOverlappingAppointments(Integer scheduleId, LocalDateTime startTime, LocalDateTime endTime);

    List<Appointments>findAllByDoctorScheduleAndStartOfAppointmentBetween(DoctorSchedule doctorSchedule,LocalDateTime start,LocalDateTime end);




}

