package com.example.firstspringbootproject.service.authors;

import com.example.firstspringbootproject.model.Author;
import com.example.firstspringbootproject.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    // Repository responsável por operações de Book na base de dados
    private final AuthorRepository authorRepository;


    // Injeção de dependências via construtor (boa prática no Spring)
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }


    /*
     * GET ALL AUTHORS
     */
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }
    /*

    /*
     * GET AUTHOR BY ID
     */
    public Optional<Author> getAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    /*
     * DELETE AUTHOR
     */
    public boolean deleteAuthor(Long id) {

        if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id);
            return true;
        }

        return false;
    }
}