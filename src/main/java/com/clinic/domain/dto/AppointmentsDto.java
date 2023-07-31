package com.clinic.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AppointmentsDto {

    private Integer id;

    private LocalDateTime startOfAppointment;//= LocalDateTime.now();

    private LocalDateTime endOfAppointment ;

    private String patientDto;

}
