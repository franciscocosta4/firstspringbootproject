
package com.example.firstspringbootproject.repository;       // Pacote onde ficam as interfaces de acesso a dados.

import com.example.firstspringbootproject.model.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository; // Interface base do Spring Data JPA.
import org.springframework.stereotype.Repository; // Anotação semântica (não obrigatória mas recomendada).

// JpaRepository<User, Long>:
//   User → a entidade que gere
//   Long → o tipo do id (deve coincidir com o @Id da entidade
//
// JpaRepository fornece estes metodos sem escrever SQL:
//   save(user)        → INSERT ou UPDATE
//   findById(id)      → SELECT WHERE id = ?
//   findAll()         → SELECT * FROM users
//   delete(user)      → DELETE

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
     // Spring Data JPA gera a query automaticamente a partir do nome do método:
    // "findBy" + "Email" → SELECT * FROM users WHERE email = ?
    // devolve Optional<User> porque o utilizador pode não existir
    
    Optional<User> findByEmail(String email); 
}