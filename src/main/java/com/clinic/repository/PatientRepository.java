package com.clinic.repository;

import com.clinic.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PatientRepository extends JpaRepository<Patient,Integer> {

    @Query("SELECT p FROM Patient p WHERE p.nId = :nId")
    Patient findByNId(@Param("nId") Integer nId);
    void deleteByDeletedTrue();
}
