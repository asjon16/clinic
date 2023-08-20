package com.clinic.repository;

import com.clinic.entity.Departments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Departments,Integer> {
    void deleteByDeletedTrue();
}
