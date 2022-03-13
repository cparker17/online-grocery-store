package com.parker.clientapplication.services;

import com.parker.clientapplication.models.security.Role;
import com.parker.clientapplication.models.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Service
public class UserService {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    @LoadBalanced
    RestTemplate restTemplate;

    public User registerAccountAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return restTemplate.postForEntity("http://USER-MICROSERVICE/user", user, User.class).getBody();
    }

    public User registerAccountUser(User user) {
        user.setRole(getRoleById(1L));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return restTemplate.postForEntity("http://USER-MICROSERVICE/user", user, User.class).getBody();
    }

    public User getUser(String username) {
        return restTemplate.getForEntity("http://USER-MICROSERVICE/user/username/" + username, User.class).getBody();
    }

    public List<User> getAllUsers() {
        ResponseEntity<User[]> response =
                restTemplate.getForEntity("http://USER-MICROSERVICE/user/all", User[].class);
        if (response.getBody() != null) {
            return List.of(response.getBody());
        } else {
            return null;
        }
    }

    public User updateUser(User user) {
        return restTemplate.patchForObject("http://USER-MICROSERVICE/user", user, User.class);
    }

    public Role getRoleById(Long id) {
        return restTemplate.getForObject("http://USER-MICROSERVICE/user/role/" + id, Role.class);
    }
}
