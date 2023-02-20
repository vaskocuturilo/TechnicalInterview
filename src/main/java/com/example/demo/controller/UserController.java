package com.example.demo.controller;

import com.example.demo.entity.UserEntity;
import com.example.demo.service.UserEntityService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Log4j2
@RequestMapping("/api/v1/admin")
public class UserController {

    private final UserEntityService userEntityService;

    public UserController(UserEntityService userEntityService) {
        this.userEntityService = userEntityService;
    }

    @GetMapping("/users")
    public String getAllTechnicalInterviewTasks(Model model) {
        List<UserEntity> userList = userEntityService.getAllUsers();

        model.addAttribute("userList", userList);

        return "admin";
    }

    @GetMapping("/active/{id}")
    public String activateNewUser(@PathVariable Long id) {
        userEntityService.setActiveStatus(id);

        return "redirect:/api/v1/admin/users";
    }

    @GetMapping("/approve/{otp}")
    public String approveUser() {

        return "main";
    }
}
