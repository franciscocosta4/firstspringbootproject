package com.example.firstspringbootproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {
    @GetMapping("/")  
    public String redirectWelcomePage() {
        return "redirect:/welcome";   // ao entrar em http://localhost:8080/ redireciona para /welcome
    }
    @GetMapping("/welcome")   
    public String welcomePage() {
        return "welcome";   // caminho para templates, abre templates/auth/welcome.html
    }

}   
