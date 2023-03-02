package com.example.demo.controller;

import com.example.demo.entity.UserEntity;
import com.example.demo.service.OneTimePasswordService;
import com.example.demo.service.UserEntityService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Log4j2
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserEntityService userEntityService;

    private final OneTimePasswordService oneTimePasswordService;

    public UserController(UserEntityService userEntityService, OneTimePasswordService oneTimePasswordService) {
        this.userEntityService = userEntityService;
        this.oneTimePasswordService = oneTimePasswordService;
    }

    @GetMapping("/all")
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

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserEntity());

        return "signup_form";
    }

    @PostMapping("/process_register")
    public String processRegister(@ModelAttribute UserEntity userEntity, Model model) {
        userEntityService.registerUser(userEntity);
        Integer oneTimePassword = oneTimePasswordService.returnOneTimePassword().getOneTimePasswordCode();

        model.addAttribute("oneTimePassword", oneTimePassword);

        return "register_success";
    }
}
