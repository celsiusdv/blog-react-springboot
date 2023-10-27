package com.users.usersmanagement.service;

import com.users.usersmanagement.entity.Permission;
import com.users.usersmanagement.entity.Role;
import com.users.usersmanagement.entity.User;
import com.users.usersmanagement.exceptions.RepeatedEmailException;
import com.users.usersmanagement.exceptions.UserNotFoundException;
import com.users.usersmanagement.repository.PermissionRepository;
import com.users.usersmanagement.repository.RoleRepository;
import com.users.usersmanagement.repository.UserRepository;
import com.users.usersmanagement.security.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenGenerator tokenService;

    //3- authenticate the user
    public User login(String username, String password){
        User user=null;
        try {
            Authentication auth =//Authenticate the user for the ProviderManager in SecurityConfig.class, @Bean AuthenticationManager
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            if(auth.isAuthenticated()){
                String token = tokenService.generateJwt(auth);
                user= (User) auth.getPrincipal();//get the user from the authentication manager after authentication
                user.setToken(token);//this entity will have a token without persisting the token in the database

            }
            return user;
        }catch (AuthenticationException e) { throw new UserNotFoundException("invalid user"); }
    }

    public User registerUser(String name, String email, String password) {
        Optional<User> existingStudent = userRepository.findUserByEmail(email);
        if (existingStudent.isPresent()) throw new RepeatedEmailException("This email is already in use");
        else {
            User recordUser = new User(name, email, passwordEncoder.encode(password), userRole("USER"));
            return userRepository.save(recordUser);
        }

    }
    private Set<Role> userRole(String role){
        Set<Role> roles = new HashSet<>();
        Set<Permission> permissions = new HashSet<>(permissionRepository.getUserPermissions());// get all permissions from database
        Optional<Role> userRole = roleRepository.findByRoleName(role);//get role from database
        if(userRole.isPresent()){
            userRole.get().setPrivileges(permissions);//set permissions to the role
            roles.add(userRole.get());//add the role with permissions in the collection to set in the User entity
        }
        return roles;
    }
}
