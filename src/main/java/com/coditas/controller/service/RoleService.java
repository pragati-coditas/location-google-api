package com.coditas.controller.service;

import com.coditas.controller.entity.Role;
import com.coditas.controller.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author Harshal Patil
 */
@Service
public class RoleService {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Resource
    RoleRepository roleRepository;

    public Optional<Role> findByName(Role.RoleName roleName) {
        return roleRepository.findByName(roleName);
    }
}
