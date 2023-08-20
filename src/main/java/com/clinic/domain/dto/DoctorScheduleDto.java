package com.clinic.domain.dto;

import com.clinic.entity.Appointments;
import com.clinic.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
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

    private LocalTime startTime;

    private LocalTime endTime;

    public DoctorScheduleDto() {
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
