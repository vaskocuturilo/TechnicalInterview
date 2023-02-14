package com.example.demo.service;

import com.example.demo.entity.UserEntity;
import com.example.demo.exception.UserFoundException;
import com.example.demo.repository.UserEntityRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
public class UserEntityService {

    final UserEntityRepository userEntityRepository;

    @Value("${user.info.message}")
    private String infoMessage;

    public UserEntityService(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    @Transactional
    public void registerUser(final UserEntity userEntity) {
        UserEntity user = userEntityRepository.findByEmail(userEntity.getEmail());
        if (user != null) {
            throw new UserFoundException(String.format(infoMessage, userEntity.getEmail()));
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(userEntity.getPassword());
        userEntity.setPassword(encodedPassword);

        userEntityRepository.save(userEntity);
    }
}
