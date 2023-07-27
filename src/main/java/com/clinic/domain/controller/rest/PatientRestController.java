package com.clinic.domain.controller.rest;

import com.clinic.domain.dto.PatientDto;
import com.clinic.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.clinic.domain.mapper.PatientMapper.toDto;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/patients")
public class PatientRestController {

    private final PatientService patientService;

    @PostMapping("/create")
    public ResponseEntity<PatientDto> createPatient(@RequestBody PatientDto p){
        return ResponseEntity.ok(patientService.create(p));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientDto> updatePatient(@PathVariable Integer id, @RequestBody PatientDto p){
        return ResponseEntity.ok(patientService.update(id,p));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDto> getPatientById(@PathVariable Integer id){
        return ResponseEntity.ok(toDto(patientService.findById(id)));
    }

    @GetMapping
    public ResponseEntity<List<PatientDto>> getPatients(){
        return ResponseEntity.ok(patientService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Integer id){
        patientService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
