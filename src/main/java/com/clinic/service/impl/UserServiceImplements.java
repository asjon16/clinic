package com.clinic.service.impl;

import com.clinic.domain.dto.UserDto;
import com.clinic.domain.exception.ResourceNotFoundException;
import com.clinic.domain.mapper.UserMapper;
import com.clinic.entity.User;
import com.clinic.repository.UserRepository;
import com.clinic.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

import static com.clinic.domain.mapper.UserMapper.toDto;
import static com.clinic.domain.mapper.UserMapper.toEntity;

@RequiredArgsConstructor
@Validated
@Service
public class UserServiceImplements implements UserService{
    private final UserRepository userRepository;
    @Override
    public UserDto create(@Valid UserDto user) {
        var result = userRepository.save(toEntity(user));
        return toDto(result);
    }

    @Override
    public UserDto update(Integer id, @Valid UserDto userDto) {
        var user = findById(id);
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setPassword(user.getPassword());
        userRepository.save(user);
        return toDto(user);
    }

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(String
                        .format("User with id %s do not exist",id)));
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(UserMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Integer id) {
        var toDelete = findById(id);
        toDelete.setDeleted(true);
        userRepository.save(toDelete);
    }
}
