package com.clinic.service;

import com.clinic.domain.dto.DepartmentsDto;
import com.clinic.domain.dto.PatientDto;
import com.clinic.entity.Departments;
import com.clinic.entity.Patient;
import jakarta.validation.Valid;

import java.util.List;

public interface DepartmentService {
    DepartmentsDto create(@Valid DepartmentsDto department);
    DepartmentsDto update(Integer id,@Valid DepartmentsDto department);
    Departments findById(Integer id);
    List<DepartmentsDto> findAll();
    void deleteById(Integer id);
}
