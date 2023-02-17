package com.example.demo.service;

import com.example.demo.entity.RoleEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.exception.UserFoundException;
import com.example.demo.repository.RolesRepository;
import com.example.demo.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Log4j2
@Service
public class UserEntityService {

    private final UserRepository userEntityRepository;
    private final RolesRepository rolesRepository;

    @Value("${user.info.message}")
    private String infoMessage;

    public UserEntityService(UserRepository userEntityRepository, RolesRepository rolesRepository) {
        this.userEntityRepository = userEntityRepository;
        this.rolesRepository = rolesRepository;
    }

    @Transactional
    public void registerUser(final UserEntity userEntity) {
        RoleEntity role = new RoleEntity();
        role.setName("GUEST");
        UserEntity user = userEntityRepository.findByEmail(userEntity.getEmail());
        if (user != null) {
            throw new UserFoundException(String.format(infoMessage, userEntity.getEmail()));
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(userEntity.getPassword());
        userEntity.setPassword(encodedPassword);

        userEntity.setRoles(Set.of(role));
        userEntityRepository.save(userEntity);
    }
}