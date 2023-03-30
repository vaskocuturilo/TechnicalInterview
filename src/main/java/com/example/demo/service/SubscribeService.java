package com.example.demo.service;

import com.example.demo.entity.SubscribeEntity;
import com.example.demo.exception.UserFoundException;
import com.example.demo.repository.SubscribeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SubscribeService {
    private final SubscribeRepository subscribeRepository;

    public SubscribeEntity createSubscribe(final SubscribeEntity subscribeEntity) {
        SubscribeEntity existSubscribe = subscribeRepository.findByEmail(subscribeEntity.getEmail());
        if (existSubscribe != null) {
            throw new UserFoundException(String.format("The user with email %s was found in subscribe list.", existSubscribe));
        }
        subscribeEntity.setActive(true);

        return subscribeRepository.save(subscribeEntity);
    }

    public SubscribeEntity createUnSubscribe(final SubscribeEntity subscribeEntity) {
        SubscribeEntity existSubscribe = subscribeRepository.findByEmail(subscribeEntity.getEmail());
        if (existSubscribe != null) {
            throw new UserFoundException(String.format("The user with email %s was found in subscribe list.", existSubscribe));
        }
        subscribeEntity.setActive(false);

        return subscribeRepository.save(subscribeEntity);
    }
}
