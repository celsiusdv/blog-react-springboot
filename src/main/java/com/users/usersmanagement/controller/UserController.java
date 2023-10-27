package com.users.usersmanagement.controller;

import com.users.usersmanagement.entity.User;
import com.users.usersmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users/api")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private final UserService userService;
    public UserController(UserService userService){
        this.userService=userService;
    }


    @GetMapping(path = "/user")
    public ResponseEntity<List<User>>getAll(){
        return new ResponseEntity<>(userService.getAllUsers(),HttpStatus.OK);
    }


    @GetMapping(path = "/user/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable("email") String email){
        return new ResponseEntity<>(userService.findUserByEmail(email), HttpStatus.OK);
    }
    
    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Integer userId,
                                           @RequestBody User user){
        return new ResponseEntity<>(userService.updateUser(userId,user),HttpStatus.OK);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") Integer userId){
        return new ResponseEntity<>(userService.deleteUser(userId),HttpStatus.OK);
    }
}