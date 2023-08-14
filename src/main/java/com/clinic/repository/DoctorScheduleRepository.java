package com.clinic.repository;

import com.clinic.entity.Appointments;
import com.clinic.entity.DoctorSchedule;
import com.clinic.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule,Integer> {

    @Query("SELECT COUNT(s) > 0 FROM DoctorSchedule s " +
            "WHERE s.id = :scheduleId " +
            "AND :appointmentStartTime >= s.startTime " +
            "AND :appointmentEndTime <= s.endTime")
    boolean isAppointmentWithinDoctorSchedule(Integer scheduleId, LocalDateTime appointmentStartTime, LocalDateTime appointmentEndTime);
}
