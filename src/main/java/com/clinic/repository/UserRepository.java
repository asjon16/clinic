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







}
