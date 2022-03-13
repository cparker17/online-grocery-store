package com.parker.usermicroservice;

import com.parker.usermicroservice.models.Role;
import com.parker.usermicroservice.models.User;
import com.parker.usermicroservice.repositories.RoleRepository;
import com.parker.usermicroservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.parker.usermicroservice.models.Role.Roles.ROLE_ADMIN;
import static com.parker.usermicroservice.models.Role.Roles.ROLE_USER;

@SpringBootApplication
@EnableEurekaClient
public class UserMicroserviceApplication {
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(UserMicroserviceApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadInitialData() {
        return (args) -> {
            if (roleRepository.findAll().isEmpty()) {
                Role USER = new Role(ROLE_USER);
                roleRepository.save(USER);
                Role ADMIN = new Role(ROLE_ADMIN);
                roleRepository.save(ADMIN);
            }
        };
    }
}
