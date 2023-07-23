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

    @NotEmpty
    @Size(max = 30,message = "Maximum size of name is 30")
    private String name;

    @Size(max = 30,message = "Maximum size of name is 30")
    private List<UserDto> doctor;
}
