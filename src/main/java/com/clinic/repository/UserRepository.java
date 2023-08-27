package com.clinic.repository;

import com.clinic.entity.DoctorSchedule;
import com.clinic.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findFirstByEmail(String email);
    @Query("SELECT u FROM User u WHERE u.doctorDepartment.id = :departmentId")
    List<User> findAllDoctorsByDepartmentId(Integer departmentId);
    void deleteByDeletedTrue();

    List<User> findAllByFirstnameContainingIgnoreCase(String name);
    List<User> findAllByLastnameContainingIgnoreCase(String name);

    @Query("SELECT u FROM User u " +
            "JOIN u.schedule ds " +
            "JOIN ds.appointments a " +
            "GROUP BY u " +
            "ORDER BY COUNT(a) DESC")
    List<User> findDoctorWithMostAppointments();

    @Query("SELECT a.doctorSchedule.doctor " +
            "FROM Appointments a " +
            "WHERE a.patient.id = :patientId " +
            "GROUP BY a.doctorSchedule.doctor " +
            "ORDER BY COUNT(a) DESC")
    List<User> findDoctorWithMostVisits(@Param("patientId") Integer patientId);
}









