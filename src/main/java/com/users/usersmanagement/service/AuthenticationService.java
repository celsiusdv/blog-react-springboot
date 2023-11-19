package com.users.usersmanagement.service;

import com.users.usersmanagement.dto.Token;
import com.users.usersmanagement.dto.LoginResponse;
import com.users.usersmanagement.entity.Privilege;
import com.users.usersmanagement.entity.RefreshToken;
import com.users.usersmanagement.entity.Role;
import com.users.usersmanagement.entity.User;
import com.users.usersmanagement.exceptions.RepeatedEmailException;
import com.users.usersmanagement.exceptions.UserNotFoundException;
import com.users.usersmanagement.repository.PrivilegeRepository;
import com.users.usersmanagement.repository.RoleRepository;
import com.users.usersmanagement.repository.UserRepository;
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
    private TokenService tokenService;

    //3- authenticate the user
    public LoginResponse login(String username, String password){
        User user=null;
        Token token =new Token();
        RefreshToken refreshToken;
        try {
            Authentication auth =//Authenticate the user for the ProviderManager in SecurityConfig.class, @Bean AuthenticationManager
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            if(auth.isAuthenticated()){
                user= (User) auth.getPrincipal();
                token.setAccessToken(tokenService.createJwtAccessToken(auth));
                refreshToken=tokenService.createUUIDRefreshToken(user.getEmail());
                token.setRefreshToken(refreshToken.getRefreshToken());
                System.out.println(user);
                //TODO: check class TokenService in the method createUUIDRefreshToken() if it is possible
                //TODO: to set a user without calling to the repository
            }
            return new LoginResponse(user, token.getAccessToken(), token.getRefreshToken());
        }catch (AuthenticationException e) { throw new UserNotFoundException("invalid user"); }
    }

    //get roles and privileges and set to the user before persisting in the method registerUser()
    private Set<Role> userRole(String roleName){
        Optional<Role> role = roleRepository.findByRoleName(roleName);//get role from database
        Set<Role> roles = new HashSet<>();     //to get admin or user permissions switch getUserPermissions() to getAdminPermissions()
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
        else {                                                        //to add an admin just replace "USER" by "ADMIN"
            User recordUser = new User(name, email, passwordEncoder.encode(password), userRole("USER"));
            return userRepository.save(recordUser);
        }
    }

    public Token refreshToken(String refreshTokenRequest){
        RefreshToken refreshToken = null;
        User user;
        String accessToken="";
        try{
            Optional<RefreshToken> checkToken=tokenService.findRefreshToken(refreshTokenRequest);
            if(checkToken.isPresent()){
                refreshToken=tokenService.verifyExpiration(checkToken.get());
                user=refreshToken.getUser();
                accessToken = tokenService.createJwtAccessToken((Authentication) user);
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException("Refresh token is not in database!");
        }
        return new Token(accessToken, refreshToken.getRefreshToken());
    }
}