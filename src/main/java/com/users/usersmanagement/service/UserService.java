package com.users.usersmanagement.service;

import com.users.usersmanagement.entity.User;
import com.users.usersmanagement.exceptions.RepeatedEmailException;
import com.users.usersmanagement.exceptions.UserNotFoundException;
import com.users.usersmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

    public User updateUser(Integer userId, User user){
        try{
            Optional<User> userToUpdate= Optional.of(
                    userRepository.getReferenceById(userId));//user from database to be replaced with new values
            userToUpdate.get().setName(user.getName());
            userToUpdate.get().setEmail(user.getEmail());
            userToUpdate.get().setPassword(user.getPassword());
            return userRepository.save(userToUpdate.get());
        }catch (Exception e){
            throw e.getLocalizedMessage().contains("ConstraintViolationException")?
                    new RepeatedEmailException("ConstraintViolationException: This email is already in use"):
                    new UserNotFoundException("User not found");

        }
    }
    public void deleteUser(Integer userId){

    }
}
