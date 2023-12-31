package com.clinic.domain.dto;

import com.clinic.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor

public class DepartmentsDto {

    private Integer id;

    @NotEmpty
    @Size(max = 30,message = "Maximum size of name is 30")
    private String name;

    public DepartmentsDto(String name) {
        this.name = name;
    }
    public DepartmentsDto(Integer id,String name) {
        this.name = name;
        this.id = id;
    }
}
