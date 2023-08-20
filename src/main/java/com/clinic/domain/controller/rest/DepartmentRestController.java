package com.clinic.domain.controller.rest;

import com.clinic.domain.dto.DepartmentsDto;
import com.clinic.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.clinic.domain.mapper.DepartmentsMapper.toDtoNoUsers;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/departments")
public class DepartmentRestController {

    private final DepartmentService departmentService;

    @PreAuthorize("hasAnyRole('ADMIN','WORKER')")
    @PostMapping("/create") // Works don't touch
    public ResponseEntity<DepartmentsDto> createDepartment(@RequestBody DepartmentsDto d){
        return ResponseEntity.ok(departmentService.createNoUsers(d));
    }
    @PreAuthorize("hasAnyRole('ADMIN','WORKER')")
    @PutMapping("/update/{id}")// Works don't touch
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

    @PreAuthorize("hasAnyRole('ADMIN','WORKER')")
    @DeleteMapping("/{id}") // needs work, nuk fshin departamentin nese ka user qe e ka si parameter
    //throw exceptions kur ka akoma users
    public ResponseEntity<Void> deleteDepartment(@PathVariable Integer id){
        departmentService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
