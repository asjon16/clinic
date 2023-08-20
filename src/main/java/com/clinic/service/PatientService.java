package com.clinic.service;

import com.clinic.domain.dto.PatientDto;
import com.clinic.entity.Patient;
import jakarta.validation.Valid;

import java.util.List;

public interface PatientService {
    PatientDto create(@Valid PatientDto patient);

    Boolean findByNId(Integer id);

    PatientDto update(Integer id, @Valid PatientDto patient);
    Patient findById(Integer id);
    List<PatientDto> findAll();
    void deleteById(Integer id);
    void deleteByDeletedTrue();
}
