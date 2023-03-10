package com.example.demo.service;

import com.example.demo.entity.RoleEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.exception.QuestionNotFoundException;
import com.example.demo.exception.UserFoundException;
import com.example.demo.repository.RolesRepository;
import com.example.demo.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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
        UserEntity user = userEntityRepository.findByEmail(userEntity.getEmail());
        RoleEntity role = new RoleEntity();
        role.setName("GUEST");
        if (user != null) {
            throw new UserFoundException(String.format(infoMessage, userEntity.getEmail()));
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(userEntity.getPassword());
        userEntity.setPassword(encodedPassword);

        userEntity.setRoles(Set.of(role));
        userEntity.setActive(false);
        userEntityRepository.save(userEntity);
    }

    public List<UserEntity> getAllUsers() {
        return userEntityRepository.findAll();
    }

    @Transactional
    public void setActiveStatus(Long id) {
        userEntityRepository.setActiveStatus(id);
    }

    public Optional<UserEntity> editUser(Long id) {
        Optional<UserEntity> userEntity = userEntityRepository.findById(id);
        userEntity
                .stream()
                .filter(question -> question.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> {
                    QuestionNotFoundException questionNotFoundException = new QuestionNotFoundException(infoMessage);
                    log.debug("The question with id = " + id + " not found." + questionNotFoundException.getMessage());
                    return questionNotFoundException;
                });

        return userEntity;
    }

    public void saveUser(UserEntity userEntity) {
        userEntityRepository.save(userEntity);
    }

    public void deleteUser(Long id) {
        userEntityRepository.deleteById(id);
    }
}