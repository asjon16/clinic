package com.clinic.configuration.rest;

import com.clinic.domain.dto.LoginRequestDto;
import com.clinic.repository.UserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Profile("rest")
@Service
public class RestAuthService {
    private final JwtEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public RestAuthService(JwtEncoder encoder, AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.encoder = encoder;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    public String generateToken(LoginRequestDto req) {
        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(),req.getPassword()));
        var user = userRepository.findFirstByEmail(req.getEmail());
        Instant now = Instant.now();
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(String.valueOf(user.get().getId()))
                .claim("roles", scope)
                .build();
        var encoderParameters = JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS512).build(), claims);
        return this.encoder.encode(encoderParameters).getTokenValue();
    }

    public static String getLoggedUser(){
        return SecurityContextHolder.getContext().getAuthentication()
                .getName();
    }

}
