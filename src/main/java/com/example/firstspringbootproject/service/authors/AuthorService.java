package com.example.firstspringbootproject.service.authors;

import com.example.firstspringbootproject.model.Author;
import com.example.firstspringbootproject.repository.AuthorRepository;
import com.example.firstspringbootproject.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    // Repository responsável por operações de Book na base de dados
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;


    // Injeção de dependências via construtor (boa prática no Spring)
    public AuthorService(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public Author createAuthor(Author author) {
        
        // save() = INSERT INTO books + INSERT INTO book_author (a tabela de junção)
        // o JPA trata da tabela de junção automaticamente porque Book tem o @JoinTable
        return authorRepository.save(author);
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

// devolve Optional<Author> para o controller poder devolver 404 se não existir
        public Optional<Author> updateAuthor(Long id, Author updatedAuthor) {
        // Primeiro vemos se o autor existe.
        return authorRepository.findById(id).map(existingAuthor -> {
            // Atualizamos os campos do livro existente com os do updateAuthor.
            existingAuthor.setName(updatedAuthor.getName());
            existingAuthor.setBiography(updatedAuthor.getBiography());
            existingAuthor.setBornDate(updatedAuthor.getBornDate());
            existingAuthor.setDeathDate(updatedAuthor.getDeathDate());

            // Guardamos novamente na BD.
            return authorRepository.save(existingAuthor);
        });
        // Se não existir, o Optional fica vazio.
    }


    /*
     * DELETE AUTHOR
     */
    public void deleteAuthor(Long id) {
        if (authorRepository.existsById(id) && bookRepository.existsByAuthors_Id(id) == false) {
            authorRepository.deleteById(id);
        }else{
            throw new IllegalStateException("Não é possível eliminar o autor porque tem livros associados.");
        }
    }
}