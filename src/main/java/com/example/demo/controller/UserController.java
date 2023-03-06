package com.example.demo.controller;

import com.example.demo.entity.UserEntity;
import com.example.demo.service.OneTimePasswordService;
import com.example.demo.service.UserEntityService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@Log4j2
@RequestMapping("/api/v1")
public class UserController {
    private final UserEntityService userEntityService;

    private final OneTimePasswordService oneTimePasswordService;

    public UserController(UserEntityService userEntityService, OneTimePasswordService oneTimePasswordService) {
        this.userEntityService = userEntityService;
        this.oneTimePasswordService = oneTimePasswordService;
    }

    @GetMapping("/users/all")
    public String getAllTechnicalInterviewTasks(Model model) {
        List<UserEntity> userList = userEntityService.getAllUsers();

        model.addAttribute("userList", userList);

        return "admin";
    }

    @PostMapping("/users/active/{id}")
    public String activateNewUser(@PathVariable Long id) {
        userEntityService.setActiveStatus(id);

        return "redirect:/api/v1/users/all";
    }

    @GetMapping("/users/approve/{otp}")
    public String approveUser() {
        return "main";
    }

    @GetMapping("/users/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserEntity());

        return "signup_form";
    }

    @PostMapping(value = "/users/{id}/edit")
    public String editUser(@PathVariable Long id, Model model) {
        Optional<UserEntity> userEntity = userEntityService.editUser(id);
        model.addAttribute("userEntity", userEntity);

        return "edit_user";
    }

    @PostMapping(value = "/users")
    public String addNewTechnicalInterviewTask(@ModelAttribute UserEntity userEntity) {
        userEntityService.saveUser(userEntity);

        return "redirect:/api/v1/users/all";
    }

    @PostMapping(value = "/users/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        userEntityService.deleteUser(id);

        return "redirect:/api/v1/users/all";
    }

    @PostMapping("/users/process_register")
    public String processRegister(@ModelAttribute UserEntity userEntity, Model model) {
        userEntityService.registerUser(userEntity);
        Integer oneTimePassword = oneTimePasswordService.returnOneTimePassword().getOneTimePasswordCode();

        model.addAttribute("oneTimePassword", oneTimePassword);

        return "register_success";
    }
}
