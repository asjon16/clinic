package com.clinic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@SpringBootApplication
@EnableScheduling
public class ProjectOnlinceClinicApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectOnlinceClinicApplication.class, args);
    }

}
