package com.users.usersmanagement.service;

import com.users.usersmanagement.entity.User;
import com.users.usersmanagement.exceptions.RepeatedEmailException;
import com.users.usersmanagement.exceptions.UserNotFoundException;
import com.users.usersmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public User createUser(User user) {
        Optional<User> existingStudent= userRepository.findUserByEmail(user.getEmail());
        if(existingStudent.isPresent()){
            throw new RepeatedEmailException("This email is already in use");
        } else return userRepository.save(user);
    }

    public List<User> getAllUsers(){
        if(userRepository.findAll().isEmpty()) {
            throw new UserNotFoundException("Empty List");
        }
        return userRepository.findAll() ;
    }

    public User findUserByEmail(String email) {
        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isPresent()) return user.get();
        else throw new UserNotFoundException("User not found");
    }
}
