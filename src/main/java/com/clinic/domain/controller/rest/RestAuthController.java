package com.clinic.domain.controller.rest;

import com.clinic.configuration.rest.RestAuthService;
import com.clinic.domain.dto.LoginRequestDto;
import com.clinic.domain.dto.RegisterForm;
import com.clinic.domain.dto.UserDto;
import com.clinic.service.UserService;
import jakarta.validation.Valid;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Profile("rest")
@RestController
@RequestMapping("/api/auth")
public class RestAuthController {

    private final RestAuthService authService;
    private final UserService userService;

    public RestAuthController(RestAuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> token(@RequestBody LoginRequestDto req) {
        String token = authService.generateToken(req);

        if (token != null) {
            return ResponseEntity.ok("Bearer " + token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register/doctor")
    public ResponseEntity<UserDto> registerDoctor(@RequestBody @Valid RegisterForm user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        UserDto registeredUser = userService.registerDetails(user);
        if (registeredUser != null) {
            return ResponseEntity.ok(registeredUser);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
    @PostMapping("/register/admin")
    public ResponseEntity<UserDto> registerAdmin(@RequestBody @Valid RegisterForm user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        UserDto registeredUser = userService.registerDetailsForAdmin(user);
        if (registeredUser != null) {
            return ResponseEntity.ok(registeredUser);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
    @PostMapping("/register/worker")
    public ResponseEntity<UserDto> registerWorker(@RequestBody @Valid RegisterForm user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        UserDto registeredUser = userService.registerDetailsForWorker(user);
        if (registeredUser != null) {
            return ResponseEntity.ok(registeredUser);
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }


}
