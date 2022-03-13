package com.parker.clientapplication;

import com.parker.clientapplication.models.security.User;
import com.parker.clientapplication.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication
@EnableEurekaClient
public class ClientApplication {
    @Autowired
    UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }

    @Bean
    public CommandLineRunner loadInitialData() {
        return (args) -> {
            List<User> allUsers = userService.getAllUsers();
            boolean hasAdmin = false;
            for (User user : allUsers) {
                if (user.getUsername().equals("admin")) {
                    hasAdmin = true;
                }
            }
            if (!hasAdmin) {
                User admin = User.builder()
                        .username("admin")
                        .password("admin")
                        .fullName("admin")
                        .email("admin@email.com")
                        .role(userService.getRoleById(2L))
                        .build();
                userService.registerAccountAdmin(admin);
            }
        };
    }
}
