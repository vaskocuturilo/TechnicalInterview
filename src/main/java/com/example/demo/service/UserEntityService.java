package com.example.demo.service;

import com.example.demo.entity.RoleEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.exception.QuestionNotFoundException;
import com.example.demo.exception.UserFoundException;
import com.example.demo.repository.OneTimePasswordRepository;
import com.example.demo.repository.RolesRepository;
import com.example.demo.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Log4j2
@Service
@Transactional
public class UserEntityService {

    private final UserRepository userEntityRepository;
    private final RolesRepository rolesRepository;
    private final OneTimePasswordRepository oneTimePasswordRepository;

    @Value("${user.info.message}")
    private String infoMessage;

    public UserEntityService(UserRepository userEntityRepository, RolesRepository rolesRepository, OneTimePasswordRepository oneTimePasswordRepository) {
        this.userEntityRepository = userEntityRepository;
        this.rolesRepository = rolesRepository;
        this.oneTimePasswordRepository = oneTimePasswordRepository;
    }

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

    public void setActiveStatus(Long id) {
        userEntityRepository.setActiveStatus(id);
    }

    public void approveUser(@RequestParam Long userId, @RequestParam Integer code) {
        UserEntity userEntity = userEntityRepository.findById(userId).get();
        Integer oneTimePasswordCodeExist = oneTimePasswordRepository.findByOneTimePasswordCode(userId);
        if (userEntity.isActive()) {
            throw new IllegalStateException("The user with id = " + userId + " was active.");
        }
        if (!oneTimePasswordCodeExist.equals(code)) {
            throw new IllegalStateException("The one time password code = " + oneTimePasswordCodeExist + " not correctly. Please, check you email.");
        }

        userEntity.setActive(!userEntity.isActive());
        oneTimePasswordRepository.deleteByOneTimePasswordCode(oneTimePasswordCodeExist);
        userEntityRepository.save(userEntity);
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