package com.example.demo.controller;

import com.example.demo.entity.TechnicalInterviewEntity;
import com.example.demo.exception.QuestionNotFoundException;
import com.example.demo.exception.StorageFileNotFoundException;
import com.example.demo.service.TechnicalInterviewService;
import com.example.demo.storage.StorageService;
import com.example.demo.upload.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/api/v1")
public class TechnicalInterviewController {

    private final TechnicalInterviewService technicalInterviewService;

    private final StorageService storageService;
    private final UploadService uploadService;

    @Value("${question.error.message}")
    private String notFoundErrorMessage;


    @Value("${storage.error.message}")
    private String storageNotFoundErrorMessage;

    @Autowired
    public TechnicalInterviewController(TechnicalInterviewService technicalInterviewService, StorageService storageService, UploadService uploadService) {
        this.technicalInterviewService = technicalInterviewService;
        this.storageService = storageService;
        this.uploadService = uploadService;
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
        if (randomQuestion.isEmpty() || randomQuestion.size() == 0) {
            throw new QuestionNotFoundException(notFoundErrorMessage);
        }

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
        if (file.isEmpty() || file == null) {
            throw new StorageFileNotFoundException(storageNotFoundErrorMessage);
        }

        storageService.store(file);
        uploadService.upload(file);

        redirectAttributes.addFlashAttribute("message", "You successfully uploaded file is: " + file.getOriginalFilename());

        return "redirect:/api/v1/questions";
    }

    @RequestMapping("/reset")
    public String resetAllCompletedTechnicalInterviewTasks() {
        technicalInterviewService.resetAllCompletedTechnicalInterviewTasks();
        return "redirect:/api/v1/questions";
    }
}