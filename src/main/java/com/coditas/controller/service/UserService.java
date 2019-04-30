package com.coditas.controller.service;

import com.coditas.controller.entity.User;
import com.coditas.controller.exception.ControllerException;
import com.coditas.controller.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Harshal Patil
 */
@Service
public class UserService {

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Resource
    UserRepository userRepository;

    public List<User> findAll() {
        logger.info("Find all Users");
        return userRepository.findAll();
    }

    public User createUser(User user) {
        if (null == user) {
            throw new ControllerException("Invalid User");
        }
        user = userRepository.save(user);
        return user;
    }

    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User findByEmail(String email) {

        if(null == email || email.isEmpty()){
            throw new ControllerException("Invalid User email " + email);
        }

        User user = userRepository.findByEmail(email)
            .orElseThrow(() ->
                    new UsernameNotFoundException("User not found with email : " + email)
            );
        return user;
    }
}
