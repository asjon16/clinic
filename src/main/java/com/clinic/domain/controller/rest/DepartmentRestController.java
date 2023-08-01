package com.clinic.domain.controller.rest;

import com.clinic.domain.dto.DepartmentsDto;
import com.clinic.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.clinic.domain.mapper.DepartmentsMapper.toDtoNoUsers;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/departments")
public class DepartmentRestController {

    private final DepartmentService departmentService;
    @PostMapping("/create") // Works don't touch
    public ResponseEntity<DepartmentsDto> createDepartment(@RequestBody DepartmentsDto d){
        return ResponseEntity.ok(departmentService.createNoUsers(d));
    }
    @PutMapping("/test/{id}")// Works don't touch
    public ResponseEntity<DepartmentsDto> updateDepartmentNoUsers(@PathVariable Integer id, @RequestBody DepartmentsDto d){
        return ResponseEntity.ok(departmentService.updateDepartmentNoUsers(id,d));
    }

    @GetMapping("/{id}") //works
    public ResponseEntity<DepartmentsDto> getDepartmentById(@PathVariable Integer id){
        return ResponseEntity.ok(toDtoNoUsers(departmentService.findById(id)));
    }

    @GetMapping // works
    public ResponseEntity<List<DepartmentsDto>> getDepartments(){
        return ResponseEntity.ok(departmentService.findAll());
    }

    @DeleteMapping("/{id}") // needs work, nuk fshin departamentin nese ka user qe e ka si parameter
    public ResponseEntity<Void> deletePatient(@PathVariable Integer id){
        departmentService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
