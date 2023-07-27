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
    public String token(@RequestBody LoginRequestDto req) {
        return "Bearer ".concat(authService.generateToken(req));
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody @Valid RegisterForm user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // If there are validation errors, return a bad request response
            return ResponseEntity.badRequest().build();
        }

        UserDto registeredUser = userService.registerDetails(user);
        if (registeredUser != null) {
            // If registration is successful, return the registered user data
            return ResponseEntity.ok(registeredUser);
        } else {
            // If registration fails (e.g., username already exists), return a conflict response
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }


}
