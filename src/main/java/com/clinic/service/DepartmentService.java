package com.clinic.service;

import com.clinic.domain.dto.DepartmentsDto;
import com.clinic.entity.Departments;
import jakarta.validation.Valid;

import java.util.List;

public interface DepartmentService {
   /* DepartmentsDto create(@Valid DepartmentsDto department);*/
    DepartmentsDto createNoUsers(@Valid DepartmentsDto department);

    DepartmentsDto updateDepartmentNoUsers(Integer id, @Valid DepartmentsDto department);
    Departments findById(Integer id);
    List<DepartmentsDto> findAll();
    void deleteById(Integer id);
}
