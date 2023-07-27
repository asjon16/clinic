package com.clinic.domain.controller.rest;

import com.clinic.domain.dto.DepartmentsDto;
import com.clinic.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.clinic.domain.mapper.DepartmentsMapper.toDto;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/departments")
public class DepartmentRestController {

    private final DepartmentService departmentService;
    @PostMapping("/create")
    public ResponseEntity<DepartmentsDto> createDepartment(@RequestBody DepartmentsDto d){
        return ResponseEntity.ok(departmentService.create(d));
    }
    @PostMapping("/test")
    public ResponseEntity<DepartmentsDto> createDepartmentNoUsers(@RequestBody DepartmentsDto d){
        return ResponseEntity.ok(departmentService.createNoUsers(d));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartmentsDto> updateDepartment(@PathVariable Integer id, @RequestBody DepartmentsDto d){
        return ResponseEntity.ok(departmentService.update(id,d));
    }
    @PutMapping("/test/{id}")
    public ResponseEntity<DepartmentsDto> updateDepartmentNoUsers(@PathVariable Integer id, @RequestBody DepartmentsDto d){
        return ResponseEntity.ok(departmentService.updateDepartmentNoUsers(id,d));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentsDto> getDepartmentById(@PathVariable Integer id){
        return ResponseEntity.ok(toDto(departmentService.findById(id)));
    }

    @GetMapping
    public ResponseEntity<List<DepartmentsDto>> getPatients(){
        return ResponseEntity.ok(departmentService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Integer id){
        departmentService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
