package com.parker.usermicroservice.services;

import com.parker.usermicroservice.models.Role;
import com.parker.usermicroservice.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    public Role getRoleById(Long id) {
        return roleRepository.findById(id).get();
    }
}
