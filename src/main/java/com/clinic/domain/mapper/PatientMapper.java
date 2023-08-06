package com.clinic.domain.mapper;

import com.clinic.domain.dto.PatientDto;
import com.clinic.domain.dto.UserDto;
import com.clinic.entity.Gender;
import com.clinic.entity.Patient;


public class PatientMapper {
    public static Patient toEntity(PatientDto p){
        return Patient.builder()
                .name(p.getName())
                .age(p.getAge())
                .nId(p.getNId())
                .gender(Gender.fromValue(p.getGender()))
                .build();
    }
    public static PatientDto toDto(Patient p){
        return PatientDto.builder()
                .id(p.getId())
                .name(p.getName())
                .age(p.getAge())
                .nId(p.getNId())
                .gender(p.getGender().name())
                .build();
    }

}
