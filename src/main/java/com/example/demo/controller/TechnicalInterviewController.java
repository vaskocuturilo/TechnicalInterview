package com.example.demo.controller;

import com.example.demo.entity.TechnicalInterviewEntity;
import com.example.demo.service.TechnicalInterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TechnicalInterviewController {
    @Autowired
    TechnicalInterviewService technicalInterviewService;

    @GetMapping("/")
    public String getAllTechnicalInterviewTasks(Model model) {
        List<TechnicalInterviewEntity> entityList = technicalInterviewService.getAllTechnicalInterviewTasks();
        model.addAttribute("entityList", entityList);
        model.addAttribute("entityListSize", entityList.size());

        return "index";
    }

    @GetMapping("/random")
    public String getRandomTechnicalInterviewTask(Model model) {
        TechnicalInterviewEntity randomTechnicalInterviewQuestion = technicalInterviewService.getRandomQuestion();
        model.addAttribute("randomTechnicalInterviewQuestion", randomTechnicalInterviewQuestion);

        return "redirect:/";
    }


    @RequestMapping("/delete/{id}")
    public String deleteTechnicalInterviewTask(@PathVariable Long id) {
        technicalInterviewService.deleteTechnicalInterviewTask(id);

        return "redirect:/";
    }

    @RequestMapping("/complete/{id}")
    public String completeTechnicalInterviewTask(@PathVariable Long id) {
        technicalInterviewService.completeTechnicalInterviewTask(true, id);

        return "redirect:/";
    }

    @PostMapping("/add")
    public String addNewTechnicalInterviewTask(@ModelAttribute TechnicalInterviewEntity technicalInterviewEntity) {
        technicalInterviewService.saveTechnicalInterviewTask(technicalInterviewEntity);

        return "redirect:/";
    }

    @PostMapping(path = "/upload", consumes = "application/x-www-form-urlencoded")
    public String uploadTechnicalInterviewTasks(TechnicalInterviewEntity technicalInterviewEntity) {

        return "redirect:/";
    }
}
