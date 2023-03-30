package com.example.demo.controller;

import com.example.demo.entity.SubscribeEntity;
import com.example.demo.service.SubscribeService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/subscribes")
public class SubscribeController {

    private final SubscribeService subscribeService;

    public SubscribeController(SubscribeService subscribeService) {
        this.subscribeService = subscribeService;
    }

    @PostMapping
    public ResponseEntity createSubscribe(@RequestBody SubscribeEntity subscribeEntity) {
        try {
            return ResponseEntity.ok().body(subscribeService.createSubscribe(subscribeEntity));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity createUnSubscribe(@RequestBody SubscribeEntity subscribeEntity) {
        try {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(subscribeService.createUnSubscribe(subscribeEntity));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(exception.getMessage());
        }
    }
}
