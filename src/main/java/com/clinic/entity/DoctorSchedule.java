package com.clinic.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class DoctorSchedule extends BaseEntity<Integer>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "doctorSchedule",cascade = CascadeType.ALL)
    private List<Appointments> appointments ;

    @OneToOne(mappedBy = "schedule",cascade = CascadeType.ALL)
    private User doctor;

    private LocalDateTime startTime ;

    private LocalDateTime endTime ;

    public DoctorSchedule(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public DoctorSchedule() {
    }
}
