package com.clinic.service.impl;

import com.clinic.domain.dto.DaysOffDto;
import com.clinic.domain.dto.UserDto;
import com.clinic.entity.DaysOff;
import com.clinic.entity.User;
import com.clinic.repository.DaysOffRepository;
import com.clinic.repository.UserRepository;
import com.clinic.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceImplementsTest {

    @SpyBean
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private DaysOffRepository daysOffRepository;

    @Test
    void setDaysOffForUser_ShouldAddDaysOff() {
        // Arrange
        DaysOffDto daysOffDto = new DaysOffDto();
        Integer userId = 1;

        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        when(daysOffRepository.save(any(DaysOff.class))).thenReturn(new DaysOff());

        UserDto result = userService.setDaysOffForUser(daysOffDto, userId);

        assertNotNull(result);

        verify(userRepository).findById(userId);
        verify(daysOffRepository).save(any(DaysOff.class));
        verify(userRepository).save(any(User.class));
    }

    @Test
    void findAllByLastname() {

        String lastname = "Memishahi";
        User user = new User();
        user.setLastname(lastname);
        when(userRepository.findAllByLastnameContainingIgnoreCase(lastname)).thenReturn(Collections.singletonList(user));

        List<UserDto> result = userService.findAllByLastname(lastname);


        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(lastname, result.get(0).getLastname());
    }

    @Test
    void findAllDoctorsByDepartmentId() {

        Integer departmentId = 1;
        User doctor = new User();
        when(userRepository.findAllDoctorsByDepartmentId(departmentId)).thenReturn(Collections.singletonList(doctor));
        List<UserDto> result = userService.findAllDoctorsByDepartmentId(departmentId);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void deleteById() {

        Integer userId = 1;
        userService.deleteById(userId);
        verify(userRepository).deleteById(userId);
    }

    @Test
    void findDoctorWithMostAppointments() {
        when(userRepository.findDoctorWithMostAppointments()).thenReturn(Collections.emptyList());
        List<UserDto> result = userService.findDoctorWithMostAppointments();

        assertNotNull(result);
    }
}
