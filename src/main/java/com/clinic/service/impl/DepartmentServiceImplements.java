package com.clinic.service.impl;

import com.clinic.domain.dto.DepartmentsDto;

import com.clinic.domain.exception.ResourceNotFoundException;
import com.clinic.entity.User;
import com.clinic.entity.Departments;
import com.clinic.entity.Gender;
import com.clinic.entity.Patient;
import com.clinic.repository.DepartmentRepository;
import com.clinic.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

import static com.clinic.domain.mapper.DepartmentsMapper.toDto;
import static com.clinic.domain.mapper.DepartmentsMapper.toEntity;


@RequiredArgsConstructor
@Validated
@Service
public class DepartmentServiceImplements implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public DepartmentsDto create(@Valid DepartmentsDto departments) {
        var result = departmentRepository.save(toEntity(departments));
        return toDto(result);
    }

    @Override
    public DepartmentsDto update(Integer id, @Valid DepartmentsDto departmentsDto) {
        var department = findById(id);
        department.setName(departmentsDto.getName());
        department.setDoctor(departmentsDto.getDoctor().stream().map(User::new).collect(Collectors.toList()));
        return toDto(department);
    }

    @Override
    public Departments findById(Integer id) {
        return departmentRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(String
                        .format("User with id %s do not exist",id)));
    }

    @Override
    public List<PatientDto> findAll() {
        return patientRepository.findAll().stream().map(PatientMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Integer id) {
        var toDelete = findById(id);
        toDelete.setDeleted(true);
        patientRepository.save(toDelete);
    }
}
