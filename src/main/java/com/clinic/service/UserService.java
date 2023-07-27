package com.clinic.service;

import com.clinic.domain.dto.RegisterForm;
import com.clinic.domain.dto.UserDto;
import com.clinic.entity.User;
import jakarta.validation.Valid;

import java.util.List;

public interface UserService {
    UserDto create(@Valid UserDto user);
    UserDto registerDetails(@Valid RegisterForm form);
    /*UserDto registerDetailsDoctor(@Valid RegisterForm form);*/
    UserDto update(Integer id,@Valid UserDto user);
    User findById(Integer id);
    List<UserDto> findAll();
    void deleteById(Integer id);
}
