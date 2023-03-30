package com.example.demo.events;

import com.example.demo.entity.OneTimePasswordEntity;
import com.example.demo.entity.RoleEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.UserRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class ApplicationEvents {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ApplicationEvents(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void createDefaultUsersApplication() {
        RoleEntity adminRole = new RoleEntity();
        adminRole.setId(1L);
        adminRole.setName("ADMIN");

        RoleEntity guestRole = new RoleEntity();
        guestRole.setId(2L);
        guestRole.setName("GUEST");

        HashSet<RoleEntity> adminPermission = new HashSet<>();
        adminPermission.add(adminRole);

        HashSet<RoleEntity> guestPermission = new HashSet<>();
        guestPermission.add(guestRole);

        OneTimePasswordEntity oneTimePassword = new OneTimePasswordEntity();
        oneTimePassword.setOneTimePasswordCode(123456);

        userRepository.save(new UserEntity(1L, "admin@qa.team", passwordEncoder.encode("123456"), "Male", true, adminPermission, oneTimePassword));
        userRepository.save(new UserEntity(2L, "guest", passwordEncoder.encode("guest"), "Male", true, guestPermission, oneTimePassword));
    }
}
