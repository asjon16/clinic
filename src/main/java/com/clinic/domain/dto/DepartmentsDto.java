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
@AllArgsConstructor
public class DepartmentsDto {

    private Integer id;

    public DepartmentsDto(String name) {
        this.name = name;
    }
    public DepartmentsDto(Integer id,String name) {
        this.name = name;
        this.id = id;
    }

    @NotEmpty
    @Size(max = 30,message = "Maximum size of name is 30")
    private String name;

    @Size(max = 30,message = "Maximum size of name is 30")
    private List<UserDto> doctor;
}
