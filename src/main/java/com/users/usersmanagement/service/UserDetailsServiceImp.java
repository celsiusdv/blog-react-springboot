package com.users.usersmanagement.service;

import com.users.usersmanagement.entity.Role;
import com.users.usersmanagement.entity.User;
import com.users.usersmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserDetailsServiceImp implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    // 5- fetch user through the @Bean AuthenticationManager from SecurityConfig.class
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user =new User();
        try{
            Optional<User> userToFind=userRepository.findUserByEmail(email);
            if(userToFind.isPresent()) user=userToFind.get();
            user.setAuthorities((Set<Role>) this.mergeAuthorities(user.getAuthorities()));
            return user;//entity that implements UserDetails
        }catch (UsernameNotFoundException e){
            throw new UsernameNotFoundException("invalid user");
        }
    }

    private Collection<? extends GrantedAuthority> mergeAuthorities(Collection<Role> roles) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Role role: roles) {//merge the parent list of role with the child list of privileges
            authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
            authorities.addAll(role.getPrivileges()
                    .stream()
                    .map(privilege -> new SimpleGrantedAuthority(role.getAuthority()+"_"+privilege.getPrivilege()))
                    .toList());
        }
        return authorities;
    }
}
