package com.example.firstspringbootproject.controller.auth; // Ajusta ao teu pacote base

// Imports essenciais para Spring MVC + Security + Thymeleaf + JPA + Validation
import com.example.firstspringbootproject.model.User; // Ajusta ao teu pacote da entidade User
import com.example.firstspringbootproject.repository.UserRepository; // Ajusta ao teu pacote

import java.net.Authenticator;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller       
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // o Spring injeta automaticamente os dois @Beans pelo construtor
    public AuthController(UserRepository ur, PasswordEncoder pe) {
        this.userRepository = ur;
        this.passwordEncoder = pe;
    }

    @GetMapping("/login")   // responde a GET /login → mostrar o formulário
    public String loginPage(Authentication auth) {
        if ( auth != null && auth.isAuthenticated()) { // se o user já tiver autenticado 
            return "redirect:/books"; 
        }   
        return "auth/login";   // caminho para templates, abre templates/auth/login.html
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        // Model = objeto que passa dados do controller para o template
        model.addAttribute("user", new User());
        // "user" é o nome com que o Thymeleaf acede ao objeto: th:object="${user}"
        // new User() cria um objeto vazio para o form fazer bind dos campos
        return "auth/register";
    }

    @PostMapping("/register")   // responde a POST /auth/register → processar o form
    public String register(@ModelAttribute User user) {
        // @ModelAttribute: o Spring lê os campos do form e preenche o objeto User automaticamente
        // user.getEmail(), user.getUsername(), user.getPassword() já têm os valores do form

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // encode() → faz o hash BCrypt

        userRepository.save(user);   //registo do user na bd

        return "redirect:/login"; //redireciona para o login, não é auth/login pois é um redirect (url, não pastas)
    }
}