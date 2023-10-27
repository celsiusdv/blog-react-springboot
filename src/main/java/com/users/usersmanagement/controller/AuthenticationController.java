package com.users.usersmanagement.controller;

import com.users.usersmanagement.entity.User;
import com.users.usersmanagement.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/authentication/api")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;


    @PostMapping(path = "/register")
    public ResponseEntity<User> createUser(@RequestBody User user){
        return new ResponseEntity<>(authenticationService
                .registerUser(user.getName(),user.getEmail(),user.getPassword()), HttpStatus.CREATED);
    }

    @PostMapping("/login") // 1- The client send a request to this endpoint, before the request is completed,it will go to a series of filters, check SecurityConfig.class,
    public ResponseEntity<User> loginUser(@RequestBody User user) {
        return new ResponseEntity<>(authenticationService
                .login(user.getUsername(), user.getPassword()),HttpStatus.OK);
    }

}