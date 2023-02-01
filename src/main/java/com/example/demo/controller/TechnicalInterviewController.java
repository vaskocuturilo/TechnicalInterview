package com.example.demo.controller;

import com.example.demo.entity.TechnicalInterviewEntity;
import com.example.demo.exception.QuestionNotFoundException;
import com.example.demo.exception.StorageFileNotFoundException;
import com.example.demo.service.TechnicalInterviewService;
import com.example.demo.storage.StorageService;
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
    @Autowired
    private final TechnicalInterviewService technicalInterviewService;

    private final StorageService storageService;

    @Value("${question.error.message}")
    private String notFoundErrorMessage;


    @Value("${storage.error.message}")
    private String storageNotFoundErrorMessage;

    @Autowired
    public TechnicalInterviewController(TechnicalInterviewService technicalInterviewService, StorageService storageService) {
        this.technicalInterviewService = technicalInterviewService;
        this.storageService = storageService;
    }

    @GetMapping("")
    public String viewHomePage() {
        return "index";
    }

    @GetMapping("/questions")
    public String getAllTechnicalInterviewTasks(Model model) {
        List<TechnicalInterviewEntity> entityList = technicalInterviewService.getAllTechnicalInterviewTasks();
        model.addAttribute("entityList", entityList);
        model.addAttribute("entityListSize", entityList.size());

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

        return "random";
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

    @PostMapping(value = "/add")
    public String addNewTechnicalInterviewTask(@ModelAttribute TechnicalInterviewEntity technicalInterviewEntity) {
        technicalInterviewService.saveTechnicalInterviewTask(technicalInterviewEntity);

        return "redirect:/api/v1/questions";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("upload") MultipartFile upload,
                                   RedirectAttributes redirectAttributes) {

        if (upload.isEmpty() || upload == null) {
            throw new StorageFileNotFoundException(storageNotFoundErrorMessage);
        }

        storageService.store(upload);
        redirectAttributes.addFlashAttribute("message", "You successfully uploaded file is: " + upload.getOriginalFilename());

        return "redirect:/api/v1/questions";
    }

    @RequestMapping("/reset")
    public String resetAllCompletedTechnicalInterviewTasks() {
        technicalInterviewService.resetAllCompletedTechnicalInterviewTasks();
        return "redirect:/api/v1/questions";
    }
}