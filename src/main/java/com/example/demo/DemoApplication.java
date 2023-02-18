package com.example.demo;

import com.example.demo.entity.RoleEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.UserRepository;
import com.example.demo.storage.StorageProperties;
import com.example.demo.storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class DemoApplication {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public DemoApplication(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    CommandLineRunner init(StorageService storageService, UserRepository userRepository) {
        return (args) -> {
            storageService.deleteAll();
            storageService.init();

            RoleEntity adminRole = new RoleEntity();
            adminRole.setId(1L);
            adminRole.setName("ADMIN");

            RoleEntity guestRole = new RoleEntity();
            guestRole.setId(2L);
            guestRole.setName("GUEST");

            HashSet adminPermission = new HashSet();
            adminPermission.add(adminRole);

            HashSet guestPermission = new HashSet();
            guestPermission.add(guestRole);

            userRepository.save(new UserEntity(1L, "admin@qa.team", passwordEncoder.encode("123456"), "Male", true, adminPermission));
            userRepository.save(new UserEntity(2L, "guest@qa.team", passwordEncoder.encode("123456"), "Male", true, guestPermission));

        };
    }
}
