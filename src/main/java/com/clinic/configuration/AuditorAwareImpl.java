package com.clinic.configuration;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<Integer> {

    @Override
    public Optional<Integer> getCurrentAuditor(){
        return SecurityUtils.getLoggedUserId()!=null?Optional.of(SecurityUtils.getLoggedUserId()):Optional.empty();
    }
}