package com.clinic.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
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

    @JsonBackReference
    @OneToMany(mappedBy = "doctorSchedule",cascade = CascadeType.ALL)
    private List<Appointments> appointments ;

    @JsonBackReference
    @OneToOne(mappedBy = "schedule",cascade = CascadeType.ALL)
    private User doctor;

    private LocalTime startTime ;

    private LocalTime endTime ;

    public DoctorSchedule(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public DoctorSchedule() {
    }

    @Override
    public String toString() {
        return "Schedule ID= " + id +
                ", startTime= " + startTime +
                ", endTime= " + endTime
                ;
    }
}
