package com.my_chat_gpt.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.my_chat_gpt.demo.Service.GeminiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin(origins = {"http://localhost:5173", "http://192.168.56.1:3000"}) // Allow requests from React app running on localhost:3000
public class GeminiController {
    @Autowired
    GeminiService giminiService; 

    @GetMapping("/prompt")
    public String getResponse(@RequestParam String prompt, @RequestParam String apiKey) {
        return giminiService.llamadaApi(prompt, apiKey);
    }
    
}
