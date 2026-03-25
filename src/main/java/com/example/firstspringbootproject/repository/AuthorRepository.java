package com.example.firstspringbootproject.repository;       // Pacote onde ficam as interfaces de acesso a dados.

import com.example.firstspringbootproject.model.Author;
import org.springframework.data.jpa.repository.JpaRepository; // Interface base do Spring Data JPA.
import org.springframework.stereotype.Repository; // Anotação semântica (não obrigatória mas recomendada).

@Repository                                   // Marca esta interface como componente de repositório do Spring.
public interface AuthorRepository extends JpaRepository<Author, Long> {
    // Extende JpaRepository parametrizado com:
    // Book  -> tipo da entidade
    // Long  -> tipo da chave primária

    // Ao extender JpaRepository, ganhas métodos prontos como:
    // save(book)        -> cria/atualiza
    // findById(id)      -> busca por id
    // findAll()         -> lista todos
    // deleteById(id)    -> apaga por id
    // Não precisa implementar nada aqui: o Spring gera a implementação em runtime.
}
