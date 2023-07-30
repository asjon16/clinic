package com.clinic.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorSchedule extends BaseEntity<Integer>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer availableHours;


    @OneToOne
    @JoinColumn(name = "doctor_id",referencedColumnName = "id")
    private User doctor;


    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL)
    private List<Appointment> appointments;


}
