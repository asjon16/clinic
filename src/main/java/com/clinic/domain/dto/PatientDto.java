package com.clinic.domain.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientDto {

    private Integer id;

    @NotEmpty
    @Size(max = 30,message = "Maximum size of name is 30")
    private String name;
    private Integer age;
    private String gender;

}
