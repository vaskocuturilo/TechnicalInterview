package com.example.demo.controller;

import com.example.demo.entity.TechnicalInterviewEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.exception.StorageFileNotFoundException;
import com.example.demo.service.OneTimePasswordService;
import com.example.demo.service.TechnicalInterviewService;
import com.example.demo.service.UserEntityService;
import com.example.demo.storage.StorageService;
import com.example.demo.upload.UploadService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Log4j2
@RequestMapping("/api/v1")
public class TechnicalInterviewController {
    private static final String NEW_ONE_TIME_PASSWORD_CODE = "A new one time password oneTimePasswordCode:{}";
    private static final String EXPIRE_DATA = "A new expires:{}";
    private final TechnicalInterviewService technicalInterviewService;
    private final UserEntityService userEntityService;
    private final StorageService storageService;
    private final UploadService uploadService;

    private final OneTimePasswordService oneTimePasswordService;

    @Value("${storage.error.message}")
    private String storageNotFoundErrorMessage;

    @Autowired
    public TechnicalInterviewController(TechnicalInterviewService technicalInterviewService, UserEntityService userEntityService, StorageService storageService, UploadService uploadService, OneTimePasswordService oneTimePasswordService) {
        this.technicalInterviewService = technicalInterviewService;
        this.userEntityService = userEntityService;
        this.storageService = storageService;
        this.uploadService = uploadService;
        this.oneTimePasswordService = oneTimePasswordService;
    }

    @GetMapping("")
    public String viewHomePage() {
        return "index";
    }

    @GetMapping("/questions")
    public String getAllTechnicalInterviewTasks(Model model) {
        List<TechnicalInterviewEntity> entityList = technicalInterviewService.getAllTechnicalInterviewTasks();

        Integer pass = technicalInterviewService.getAllTechnicalInterviewQuestionStatusPass();
        Integer fail = technicalInterviewService.getAllTechnicalInterviewQuestionStatusFail();

        model.addAttribute("entityList", entityList);
        model.addAttribute("entityListSize", entityList.size());

        model.addAttribute("pass", pass);
        model.addAttribute("fail", fail);

        return "main";
    }

    @GetMapping("/random")
    public String getRandomTechnicalInterviewTask(final Model model) {
        List<TechnicalInterviewEntity> randomQuestion = technicalInterviewService.getRandomQuestion();

        model.addAttribute("randomQuestion", randomQuestion);
        model.addAttribute("randomQuestionSize", randomQuestion.size());

        return "question";
    }

    @RequestMapping(value = "/edit/{id}")
    public String editTechnicalInterviewTask(@PathVariable Long id,
                                             @ModelAttribute TechnicalInterviewEntity technicalInterviewEntity) {

        technicalInterviewService.editTechnicalInterviewTask(technicalInterviewEntity, id);

        return "edit";
    }

    @RequestMapping(value = "/delete/{id}")
    public String deleteTechnicalInterviewTask(@PathVariable Long id) {
        technicalInterviewService.deleteTechnicalInterviewTask(id);

        return "redirect:/api/v1/questions";
    }

    @GetMapping(value = "/questions/delete/all")
    public String deleteTechnicalInterviewQuestions() {
        technicalInterviewService.deleteTechnicalInterviewQuestions();

        return "redirect:/api/v1/questions";
    }

    @RequestMapping(value = "/complete/{id}")
    public String completeTechnicalInterviewTask(@PathVariable Long id) {
        technicalInterviewService.completeTechnicalInterviewTask(true, id);

        return "redirect:/api/v1/questions";
    }

    @PostMapping(value = "/questions")
    public String addNewTechnicalInterviewTask(@ModelAttribute TechnicalInterviewEntity technicalInterviewEntity) {
        technicalInterviewService.saveTechnicalInterviewTask(technicalInterviewEntity);

        return "redirect:/api/v1/questions";
    }

    @PostMapping("/questions/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        if (file.isEmpty() || file.getSize() == 0) {
            throw new StorageFileNotFoundException(storageNotFoundErrorMessage);
        }

        storageService.store(file);
        uploadService.upload(file);

        redirectAttributes.addFlashAttribute("message", "You successfully uploaded file is: " + file.getOriginalFilename());

        return "redirect:/api/v1/questions";
    }

    @GetMapping("/reset")
    public String resetAllCompletedTechnicalInterviewTasks() {
        technicalInterviewService.resetAllCompletedTechnicalInterviewTasks();
        return "redirect:/api/v1/questions";
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