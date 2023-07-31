package com.clinic.domain.dto;

import com.clinic.entity.Appointments;
import com.clinic.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter

public class DoctorScheduleDto {
    private Integer id;

    private List<String> appointments ;

    private String doctor;

    private LocalDateTime startTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(8,0,0));

    private LocalDateTime endTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(17,0,0));

    public DoctorScheduleDto() {
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
