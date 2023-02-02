package com.example.demo.controller;

import com.example.demo.entity.TechnicalInterviewEntity;
import com.example.demo.exception.QuestionNotFoundException;
import com.example.demo.exception.StorageFileNotFoundException;
import com.example.demo.service.TechnicalInterviewService;
import com.example.demo.storage.StorageService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
@RequestMapping("/api/v1")
public class TechnicalInterviewController {

    private static final Logger logger = LogManager.getLogger(TechnicalInterviewController.class);

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
    public String handleFileUpload(@RequestParam("upload") MultipartFile file,
                                   RedirectAttributes redirectAttributes) throws FileNotFoundException {

        if (file.isEmpty() || file == null) {
            throw new StorageFileNotFoundException(storageNotFoundErrorMessage);
        }

        storageService.store(file);
        redirectAttributes.addFlashAttribute("message", "You successfully uploaded file is: " + file.getOriginalFilename());

        return "redirect:/api/v1/questions";
    }

    @RequestMapping("/reset")
    public String resetAllCompletedTechnicalInterviewTasks() {
        technicalInterviewService.resetAllCompletedTechnicalInterviewTasks();
        return "redirect:/api/v1/questions";
    }

    /*
     *This functionality works with static file from resource.
     * TO_DO LIST:
     * 1. Load file from upload-dir
     * 2. Check upload to database functionality
     */
    private void UploadFileToDatabase() {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<TechnicalInterviewEntity>> typeReference = new TypeReference<List<TechnicalInterviewEntity>>() {
        };
        InputStream inputStream = TypeReference.class.getResourceAsStream("/json/1.json");
        try {
            List<TechnicalInterviewEntity> technicalInterviewEntities = mapper.readValue(inputStream, typeReference);
            technicalInterviewService.saveTechnicalInterviewTasks(technicalInterviewEntities);
            logger.info("technicalInterviewEntities was save: {}", technicalInterviewEntities);
        } catch (IOException e) {
            if (logger.isDebugEnabled()) {
                logger.debug("Log Message - Method UploadFileToDatabase: {}", e.getMessage());
            }
        }
    }
}