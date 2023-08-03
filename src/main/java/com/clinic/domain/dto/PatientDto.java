package com.clinic.domain.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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

    @Min(value =0 , message = "Mosha nuk mund te jet me e vogel se 0")
    @Max(value =130, message = "Mosha nuk mund te jete me shume se 130")
    private Integer age;
    private String gender;

}
