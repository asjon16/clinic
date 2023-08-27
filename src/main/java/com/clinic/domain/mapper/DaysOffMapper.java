package com.clinic.domain.mapper;

import com.clinic.domain.dto.DaysOffDto;
import com.clinic.entity.DaysOff;

public class DaysOffMapper {

    public static DaysOffDto toDto(DaysOff d){
        DaysOffDto daysOffDto = new DaysOffDto();
        daysOffDto.setDaysOff(d.getDaysOff());
        daysOffDto.setId(d.getId());
        return daysOffDto;
    }

    public static DaysOff toEntity(DaysOffDto d){
        DaysOff daysOff = new DaysOff();
        daysOff.setDaysOff(d.getDaysOff());
        return daysOff;
    }


}
