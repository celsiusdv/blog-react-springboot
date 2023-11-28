package com.users.usersmanagement.service;

import com.users.usersmanagement.dto.Token;
import com.users.usersmanagement.dto.LoginResponse;
import com.users.usersmanagement.entity.Privilege;
import com.users.usersmanagement.entity.RefreshToken;
import com.users.usersmanagement.entity.Role;
import com.users.usersmanagement.entity.User;
import com.users.usersmanagement.exceptions.RepeatedEmailException;
import com.users.usersmanagement.exceptions.TokenException;
import com.users.usersmanagement.exceptions.UserNotFoundException;
import com.users.usersmanagement.repository.PrivilegeRepository;
import com.users.usersmanagement.repository.RoleRepository;
import com.users.usersmanagement.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
<<<<<<< HEAD
                String token = tokenService.generateJwt(auth);
                //get the user with modified roles from the authentication manager after authentication
                //check UserDetailsServiceImp -> mergeAuthorities() method to understand the roles modification
                user= (User) auth.getPrincipal();
                user.setToken(token);//this entity will have a token without persisting the token in the database
                System.out.println(user);
=======
                user= (User) auth.getPrincipal();
                token.setAccessToken(tokenService.createJwtAccessToken(user));
                refreshToken=tokenService.createUUIDRefreshToken(user);
                token.setRefreshToken(refreshToken.getRefreshToken());
                log.info("authenticated user: "+ user);
>>>>>>> authBranch
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
<<<<<<< HEAD
        if (existingUser.isPresent()) throw new RepeatedEmailException("This email is already in use");
        else {
=======
        if (existingUser.isPresent()) {
            log.error("email is already in use :(");
            throw new RepeatedEmailException("This email is already in use :(");
        }
        else {                                                        //to add an admin just replace "USER" by "ADMIN"
>>>>>>> authBranch
            User recordUser = new User(name, email, passwordEncoder.encode(password), userRole("USER"));
            return userRepository.save(recordUser);
        }
    }

    public Token refreshToken(String refreshTokenRequest){
        RefreshToken refreshToken;
        User user;
        String accessToken="";
        try{
            Optional<RefreshToken> checkToken=tokenService.findRefreshToken(refreshTokenRequest);
            if(checkToken.isPresent() && refreshTokenRequest != null){
                refreshToken= checkToken.get();
                log.info("user with token id:"+refreshToken.getRefreshTokenId()+" is requesting a refresh");
                //returns a token (which contains a user), or an exception if it has expired
                user=tokenService.verifyExpiration(refreshToken).getUser();
                accessToken = tokenService.createJwtAccessToken(user);
                log.info("generated new access token: "+accessToken);
            } else throw new TokenException("invalid token or user is null");
        }catch(Exception e){
            log.error(e.getMessage());
            throw new TokenException(e.getMessage());
        }
        return new Token(accessToken, refreshToken.getRefreshToken());
    }
}