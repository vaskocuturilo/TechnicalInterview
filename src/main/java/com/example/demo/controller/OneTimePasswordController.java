package com.example.demo.controller;

import com.example.demo.service.OneTimePasswordService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/one-time-password")
public class OneTimePasswordController {
    private final OneTimePasswordService oneTimePasswordService;

    public OneTimePasswordController(OneTimePasswordService oneTimePasswordService) {
        this.oneTimePasswordService = oneTimePasswordService;
    }

    @GetMapping("/all")
    public ResponseEntity getAllOneTimePasswords() {
        try {
            oneTimePasswordService.getAllOneTimePasswords();
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("");
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body("");
        }
    }
}
