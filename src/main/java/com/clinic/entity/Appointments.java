package com.clinic.entity;

import com.clinic.domain.dto.DoctorScheduleDto;
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
@NoArgsConstructor
public class Appointments extends BaseEntity<Integer>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime startOfAppointment;//= LocalDateTime.now();

    private LocalDateTime endOfAppointment ;//=startOfAppointment.plusHours(1);

    @ManyToOne
    @JoinColumn(name = "schedule_id",referencedColumnName = "id")
    private DoctorSchedule doctorSchedule;

    public Appointments(LocalDateTime startOfAppointment, LocalDateTime endOfAppointment, Patient patient) {
        this.startOfAppointment = startOfAppointment;
        this.endOfAppointment = endOfAppointment;
        this.patient = patient;
    }

    public Appointments(LocalDateTime startOfAppointment, LocalDateTime endOfAppointment) {
        this.startOfAppointment = startOfAppointment;
        this.endOfAppointment = endOfAppointment;
    }

    @OneToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;


}
