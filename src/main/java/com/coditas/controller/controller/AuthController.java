package com.coditas.controller.controller;

import com.coditas.controller.entity.LoginRequest;
import com.coditas.controller.entity.Role;
import com.coditas.controller.entity.User;
import com.coditas.controller.exception.ControllerException;
import com.coditas.controller.security.JwtTokenProvider;
import com.coditas.controller.security.UserPrincipal;
import com.coditas.controller.service.RoleService;
import com.coditas.controller.service.UserService;
import com.coditas.controller.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;

/**
 * @author Harshal Patil
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleService roleService;

    @PostMapping("/login")
    public ApiResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        ApiResponse apiResponse = new ApiResponse(true);
        String jwt = null;
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            jwt = jwtTokenProvider.generateToken(authentication);

            UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            apiResponse.addData("token",jwt);
            apiResponse.addData("firstName", userPrincipal.getFirstName());
            apiResponse.addData("lastName", userPrincipal.getLastName());
            apiResponse.setMessage("Login Successfuly");
        } catch (AuthenticationException e) {
            e.printStackTrace();
            apiResponse.setError(e.getMessage(), "401");
        }
        return apiResponse;
    }

    @PostMapping("/register")
    public ApiResponse registerUser(@Valid @RequestBody User user) {


        ApiResponse apiResponse = new ApiResponse(true);
        if(userService.existsByEmail(user.getEmail())) {
            return apiResponse.setError("Email already exists", "200");
        }

        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            Role userRole = roleService.findByName(Role.RoleName.ROLE_USER)
                    .orElseThrow(() -> new ControllerException("User Role not set."));

            user.setRoles(Collections.singleton(userRole));

            userService.createUser(user);
            apiResponse.setMessage("User registered successfully");
        } catch (ControllerException e) {
            e.printStackTrace();
            apiResponse.setError(e.getMessage(), e.getErrorCode());
        }

        return apiResponse;
    }
}
