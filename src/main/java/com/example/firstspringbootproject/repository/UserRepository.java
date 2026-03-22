
package com.example.firstspringbootproject.repository;       // Pacote onde ficam as interfaces de acesso a dados.

import com.example.firstspringbootproject.model.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository; // Interface base do Spring Data JPA.
import org.springframework.stereotype.Repository; // Anotação semântica (não obrigatória mas recomendada).


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}