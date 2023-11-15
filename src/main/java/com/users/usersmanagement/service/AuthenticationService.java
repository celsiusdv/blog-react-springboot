package com.users.usersmanagement.service;

import com.users.usersmanagement.entity.Privilege;
import com.users.usersmanagement.entity.Role;
import com.users.usersmanagement.entity.User;
import com.users.usersmanagement.exceptions.RepeatedEmailException;
import com.users.usersmanagement.exceptions.UserNotFoundException;
import com.users.usersmanagement.repository.PrivilegeRepository;
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
    private PrivilegeRepository privilegeRepository;
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
                //get the user with modified roles from the authentication manager after authentication
                //check UserDetailsServiceImp -> mergeAuthorities() method to understand the roles modification
                user= (User) auth.getPrincipal();
                user.setToken(token);//this entity will have a token without persisting the token in the database
                System.out.println(user);
            }
            return user;
        }catch (AuthenticationException e) { throw new UserNotFoundException("invalid user"); }
    }

    //get roles and privileges and set to the user before persisting in the method registerUser()
    private Set<Role> userRole(String roleName){
        Optional<Role> role = roleRepository.findByRoleName(roleName);//get role from database
        Set<Role> roles = new HashSet<>();
        Set<Privilege> privileges = new HashSet<>(privilegeRepository.getUserPermissions());// get all privileges from database
        if(role.isPresent()){
            role.get().setPrivileges(privileges);//add child list of privileges to the role
            roles.add(role.get());//add the role with child list of privileges to set in the User entity
        }
        return roles;
    }

    public User registerUser(String name, String email, String password) {
        Optional<User> existingUser = userRepository.findUserByEmail(email);
        if (existingUser.isPresent()) throw new RepeatedEmailException("This email is already in use");
        else {
            User recordUser = new User(name, email, passwordEncoder.encode(password), userRole("USER"));
            return userRepository.save(recordUser);
        }

    }
}
