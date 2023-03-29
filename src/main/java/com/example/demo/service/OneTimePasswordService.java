package com.example.demo.service;

import com.example.demo.entity.OneTimePasswordEntity;
import com.example.demo.repository.OneTimePasswordRepository;
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

    @Autowired
    public OneTimePasswordService(OneTimePasswordRepository oneTimePasswordRepository) {
        this.oneTimePasswordRepository = oneTimePasswordRepository;
    }

    public OneTimePasswordEntity returnOneTimePassword() {
        OneTimePasswordEntity oneTimePassword = new OneTimePasswordEntity();

        oneTimePassword.setOneTimePasswordCode(oneTimePasswordHelpService.createRandomOneTimePassword().get());
        oneTimePassword.setExpires(new Date(System.currentTimeMillis() + expiryInterval));

        oneTimePasswordRepository.save(oneTimePassword);

        return oneTimePassword;
    }

    public List<OneTimePasswordEntity> getAllOneTimePasswords(){
        return oneTimePasswordRepository.findAll();
    }
}
