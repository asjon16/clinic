package com.clinic.repository;

import com.clinic.entity.Appointments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointments,Integer> {
}
