package com.clinic.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends BaseEntity<Integer>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "first_name")
    private String firstname;
    @Column(name = "last_name")
    private String lastname;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "password")
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @ManyToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    private Departments doctorDepartment;

    // @OneToMany(mappedBy = "doctor",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @OneToOne(mappedBy = "doctor",cascade = CascadeType.ALL)
    private DoctorSchedule schedule;

    public User(String lastname) {
        this.lastname = lastname;
    }
    /*@ManyToMany(mappedBy = "students",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Course> studentCourses=new ArrayList<>();*/
}
