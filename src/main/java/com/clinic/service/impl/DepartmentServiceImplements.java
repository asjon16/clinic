package com.clinic.service.impl;

import com.clinic.domain.dto.DepartmentsDto;

import com.clinic.domain.exception.PermissionNotAllowedException;
import com.clinic.domain.exception.ResourceAlreadyExistsException;
import com.clinic.domain.exception.ResourceNotFoundException;
import com.clinic.domain.mapper.DepartmentsMapper;
import com.clinic.domain.mapper.UserMapper;
import com.clinic.entity.User;
import com.clinic.entity.Departments;
import com.clinic.entity.Gender;
import com.clinic.entity.Patient;
import com.clinic.repository.DepartmentRepository;
import com.clinic.repository.UserRepository;
import com.clinic.service.DepartmentService;
import com.clinic.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.clinic.domain.mapper.DepartmentsMapper.*;


@RequiredArgsConstructor
@Validated
@Service
public class DepartmentServiceImplements implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final UserRepository userService;



    @Override //Works don't touch -- updated version
    public DepartmentsDto createNoUsers(@Valid DepartmentsDto departments) {
        var result = toEntityNoUsers(departments);
        List<DepartmentsDto> allDepartments = findAll();
        if (allDepartments==null ||(allDepartments.stream().noneMatch(d -> d.getName().equalsIgnoreCase(departments.getName())))){
            departmentRepository.save(result);
        }else{
            throw new ResourceAlreadyExistsException("That department already exists!");
        }
        return toDtoNoUsers(result);
    }


    @Override // Works don't touch
    public DepartmentsDto updateDepartmentNoUsers(Integer id, @Valid DepartmentsDto departmentsDto) {
        var department = findById(id);
        department.setName(departmentsDto.getName());
        departmentRepository.save(department);
        return toDtoNoUsers(department);
    }

    @Override // Works don't touch
    public Departments findById(Integer id) {
        return departmentRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(String
                        .format("Department with id %s do not exist",id)));
    }

    @Override // Works don't touch
    public List<DepartmentsDto> findAll() {
        return departmentRepository.findAll().stream().map(DepartmentsMapper::toDtoNoUsers).collect(Collectors.toList());
    }

    @Override // Works don't touch
    public void deleteById(Integer id) {
        var toDelete = findById(id);
      if (userService.findAllDoctorsByDepartmentId(id).isEmpty()){
          toDelete.setDeleted(true);
          departmentRepository.save(toDelete);
      }else {
          throw new PermissionNotAllowedException("Department cannot be deleted because it's assigned to active users.");
      }
    }

    @Scheduled(cron = "0 0 0 */30 * ?")
    @Override
    public void deleteByDeletedTrue() {
        departmentRepository.deleteByDeletedTrue();
    }
}
