package com.example.firstspringbootproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.firstspringbootproject.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity // ativa o módulo Spring Security na aplicação
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService uds) {
        this.userDetailsService = uds; // injeção pelo construtor
    }

    @Bean // @Bean = diz ao Spring para gerir este objeto no seu container
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
                // AuthenticationConfiguration já sabe do UserDetailsService e PasswordEncoder
                // porque estão registados como @Bean → não precisamos de configurar manualmente
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // SecurityFilterChain = cadeia de filtros que interceta cada pedido HTTP
        http
            .authorizeHttpRequests(auth -> auth // Isto significa: “quando me deres o auth… “…eu faço estas configurações com ele”
                // auth -> auth é um lambda (função anonima do java) : recebe o configurador e defines as regras
                .requestMatchers("/", "/welcome", "/register", "/login", "/css/**").permitAll()
                // requestMatchers: define a que URLs esta regra se aplica
                // "/css/**" → o ** significa "qualquer coisa a partir daqui"
                // permitAll() → qualquer pessoa pode aceder, mesmo sem login
                .anyRequest().authenticated()
                // anyRequest() → todos os outros URLs
                // authenticated() → só utilizadores com sessão ativa
            )
            .formLogin(form -> form
                .loginPage("/login")  // usa o meu template em vez do padrão do Spring
                .defaultSuccessUrl("/books", true)  // redireciona aqui após login com sucesso
                 // true = força sempre este redirect (mesmo que viesse de outro URL)
                .permitAll()
            )   
            .logout(logout -> logout
                .logoutSuccessUrl("/welcome?logout")  // após logout redireciona para o login
                 // ?logout é um query param que o Thymeleaf lê para mostrar a mensagem "sessão terminada"
                .permitAll()
            );

        return http.build();   // constrói e devolve a cadeia de filtros configurada
    }
}