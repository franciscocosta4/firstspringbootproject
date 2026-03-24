package com.example.firstspringbootproject.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    // UserDetails é uma interface do Spring Security
    // ao implementá-la, o Spring sabe como ler o teu utilizador para autenticação

    @Id // esta é a chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // esta é a chave primária
    private Long id;

    @Column(unique = true, nullable = false)  // na BD: coluna com restrição UNIQUE e NOT NULL
    private String email;

    @Column(nullable = false)
    private String password; // vai guardar o hash BCrypt, nunca a password em texto simples

    @Column(nullable = false)
    private String username; // nome de display, não é usado no login

    // UserDetails — obrigatório implementar:

    @Override
    // Collection<? extends GrantedAuthority> = lista de permissões/roles
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(); // sem roles por agora → lista vazia
    }

    @Override
    public String getUsername() {
        return email;
    } // IMPORTANTE: o Spring usa getUsername() para identificar o utilizador 
    // nós devolvemos o email, que é o nosso campo de login


    // estes 4 métodos controlam o estado da conta
    // todos devolvem true = conta ativa e sem restrições

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // getters / setters normais
    public String getPassword() {
        return password;
    }

    public String getDisplayName() {
        return username;
    }

    public void setEmail(String e) {
        this.email = e;
    }

    public void setPassword(String p) {
        this.password = p;
    }

    public void setUsername(String u) {
        this.username = u;
    }

    public String getEmail() {
        return email;
    }
}