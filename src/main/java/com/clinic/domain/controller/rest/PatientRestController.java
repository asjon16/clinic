package com.clinic.domain.controller.rest;

import com.clinic.domain.dto.PatientDto;
import com.clinic.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.clinic.domain.mapper.PatientMapper.toDto;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/patients")
public class PatientRestController {

    private final PatientService patientService;

    @PostMapping("/create") // works
    public ResponseEntity<PatientDto> createPatient(@RequestBody PatientDto p){
        return ResponseEntity.ok(patientService.create(p));
    }

    @PutMapping("/{id}") // works
    public ResponseEntity<PatientDto> updatePatient(@PathVariable Integer id, @RequestBody PatientDto p){
        return ResponseEntity.ok(patientService.update(id,p));
    }

    @GetMapping("/{id}") // works
    public ResponseEntity<PatientDto> getPatientById(@PathVariable Integer id){
        return ResponseEntity.ok(toDto(patientService.findById(id)));
    }

    @GetMapping // works
    public ResponseEntity<List<PatientDto>> getPatients(){
        return ResponseEntity.ok(patientService.findAll());
    }

    @DeleteMapping("/{id}") // does it's job (turns deleted to true)
    public ResponseEntity<Void> deletePatient(@PathVariable Integer id){
        patientService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteAll") // does it's job (turns deleted to true)
    public ResponseEntity<Void> performHardDeletion(){
        patientService.deleteByDeletedTrue();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
