package com.example.demo.service;

import com.example.demo.entity.OneTimePasswordEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.OneTimePasswordRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.util.OneTimePasswordHelpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OneTimePasswordService {

    private final Long expiryInterval = 5L * 60 * 1000;

    private final OneTimePasswordRepository oneTimePasswordRepository;

    OneTimePasswordHelpService oneTimePasswordHelpService;

    private final UserRepository userRepository;

    @Autowired
    public OneTimePasswordService(OneTimePasswordRepository oneTimePasswordRepository, UserRepository userRepository) {
        this.oneTimePasswordRepository = oneTimePasswordRepository;
        this.userRepository = userRepository;
    }

    public OneTimePasswordEntity returnOneTimePassword(Long userId) {
        OneTimePasswordEntity oneTimePassword = new OneTimePasswordEntity();

        UserEntity user = userRepository.findById(userId).get();

        oneTimePassword.setOneTimePasswordCode(oneTimePasswordHelpService.createRandomOneTimePassword().get());
        oneTimePassword.setExpires(new Date(System.currentTimeMillis() + expiryInterval));

        oneTimePassword.setUser(user);
        oneTimePasswordRepository.save(oneTimePassword);

        return oneTimePassword;
    }

    public List<OneTimePasswordEntity> getAllOneTimePasswords(){
        return oneTimePasswordRepository.findAll();
    }
}
