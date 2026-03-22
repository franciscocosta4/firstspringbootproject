package com.example.firstspringbootproject.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;


@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String username;

    // UserDetails — obrigatório implementar:

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(); // sem roles por agora
    }

    @Override
    public String getUsername() { return email; } // login feito por email

    @Override public boolean isAccountNonExpired()  { return true; }
    @Override public boolean isAccountNonLocked()   { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled()            { return true; }

    // getters / setters normais
    public String getPassword()  { return password; }
    public String getDisplayName() { return username; }
    public void setEmail(String e)    { this.email = e; }
    public void setPassword(String p) { this.password = p; }
    public void setUsername(String u) { this.username = u; }
    public String getEmail() { return email; }
}