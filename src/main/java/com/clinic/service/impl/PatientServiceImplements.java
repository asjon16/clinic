package com.clinic.service.impl;

import com.clinic.domain.dto.PatientDto;
import com.clinic.domain.exception.ResourceAlreadyExistsException;
import com.clinic.domain.exception.ResourceNotFoundException;
import com.clinic.domain.mapper.PatientMapper;
import com.clinic.entity.Gender;
import com.clinic.entity.Patient;
import com.clinic.repository.PatientRepository;
import com.clinic.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.util.List;
import java.util.stream.Collectors;

import static com.clinic.domain.mapper.PatientMapper.toDto;
import static com.clinic.domain.mapper.PatientMapper.toEntity;


@RequiredArgsConstructor
@Validated
@Service
public class PatientServiceImplements implements PatientService {

    private final PatientRepository patientRepository;

    @Override
    public PatientDto create(@Valid PatientDto patient) {
        var result = toEntity(patient);
        if (findByNId(patient.getNId())){
            patientRepository.save(result);
        }else {
            throw new ResourceAlreadyExistsException("Patient already exists.");
        }

     return toDto(result);
    }

    @Override
    public Boolean findByNId(Integer id) {
        if (patientRepository.findByNId(id) == null) {
            return true;
        } else return false;
    }


    @Override
    public PatientDto update(Integer id, @Valid PatientDto patientDto) {
        var patient = findById(id);
        patient.setName(patientDto.getName());
        patient.setAge(patientDto.getAge());
        patient.setGender(Gender.valueOf(patientDto.getGender()));
        patientRepository.save(patient);
        return toDto(patient);
    }

    @Override
    public Patient findById(Integer id) {
        return patientRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(String
                        .format("Patient with id %s do not exist",id)));
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
