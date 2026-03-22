package com.example.firstspringbootproject.controller.auth; // Ajusta ao teu pacote base

// Imports essenciais para Spring MVC + Security + Thymeleaf + JPA + Validation
import com.example.firstspringbootproject.model.User; // Ajusta ao teu pacote da entidade User
import com.example.firstspringbootproject.repository.UserRepository; // Ajusta ao teu pacote
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository ur, PasswordEncoder pe) {
        this.userRepository = ur;
        this.passwordEncoder = pe;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login"; // templates/auth/login.html
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "auth/register"; // templates/auth/register.html
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/auth/login";
    }
}