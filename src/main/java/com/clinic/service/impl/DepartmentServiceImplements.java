package com.clinic.service.impl;

import com.clinic.domain.dto.DepartmentsDto;

import com.clinic.domain.exception.ResourceNotFoundException;
import com.clinic.domain.mapper.DepartmentsMapper;
import com.clinic.domain.mapper.UserMapper;
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

import static com.clinic.domain.mapper.DepartmentsMapper.*;


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
    public DepartmentsDto createNoUsers(@Valid DepartmentsDto departments) {
        var result = departmentRepository.save(toEntityNoUsers(departments));
        return toDtoNoUsers(result);
    }

    @Override
    public DepartmentsDto update(Integer id, @Valid DepartmentsDto departmentsDto) {
        var department = findById(id);
        department.setName(departmentsDto.getName());
        department.setDoctor(departmentsDto.getDoctor().stream().map(UserMapper::toEntity).collect(Collectors.toList()));
        return toDto(department);
    }
    @Override
    public DepartmentsDto updateDepartmentNoUsers(Integer id, @Valid DepartmentsDto departmentsDto) {
        var department = findById(id);
        department.setName(departmentsDto.getName());
        departmentRepository.save(department);
        return toDtoNoUsers(department);
    }

    @Override
    public Departments findById(Integer id) {
        return departmentRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(String
                        .format("Department with id %s do not exist",id)));
    }

    @Override
    public List<DepartmentsDto> findAll() {
        return departmentRepository.findAll().stream().map(DepartmentsMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Integer id) {
        var toDelete = findById(id);
       /* toDelete.setDeleted(true);*/
        departmentRepository.delete(toDelete);
    }
}
