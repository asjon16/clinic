package com.clinic.repository;

import com.clinic.entity.DoctorSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule,Integer> {

}
