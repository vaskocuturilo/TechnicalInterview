package com.example.demo.security;

import com.example.demo.entity.UserEntity;
import com.example.demo.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userEntityRepository.findByEmail(username);
        if (userEntity == null) {
            throw new UsernameNotFoundException("The user with email = " + username + "Not found");
        }
        return new CustomUserDetails(userEntity);
    }
}
