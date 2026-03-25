package com.example.firstspringbootproject.service.books;

import com.example.firstspringbootproject.dto.books.BookRequest;
import com.example.firstspringbootproject.model.Author;
import com.example.firstspringbootproject.model.Book;
import com.example.firstspringbootproject.repository.AuthorRepository;
import com.example.firstspringbootproject.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class BookService {

    // Repository responsável por operações de Book na base de dados
    private final BookRepository bookRepository;

    // Repository responsável por operações de Author na base de dados
    private final AuthorRepository authorRepository;

    // Injeção de dependências via construtor (boa prática no Spring)
    public BookService(BookRepository bookRepository,AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public Book createBook(BookRequest request) {
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setYear(request.getYear());

        // findAllById = SELECT * FROM authors WHERE id IN (1, 2, 3)
        // devolve List<Author> — só os que existem na BD
        // new HashSet<>() converte para Set (sem duplicados) como o Book.authors exige
        Set<Author> authors = new HashSet<>(
            authorRepository.findAllById(request.getAuthorIds())
        );
        book.setAuthors(authors);

        // save() = INSERT INTO books + INSERT INTO book_author (a tabela de junção)
        // o JPA trata da tabela de junção automaticamente porque Book tem o @JoinTable
        return bookRepository.save(book);
    }


    /*
     * GET ALL BOOKS
     */
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    /*
     * 
     * /*
     * GET BOOK BY ID
     */
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }
// devolve Optional<Book> para o controller poder devolver 404 se não existir
    public Optional<Book> updateBook(Long id, BookRequest request) {

        // findById devolve Optional<Book>
        // .map() só executa se o Optional tiver valor (livro existe)
        // dentro do .map() recebemos o book e fazemos as alterações
        return bookRepository.findById(id)
            .map(book -> {
                // lambda: book é o Book encontrado na BD
                book.setTitle(request.getTitle());
                book.setYear(request.getYear());

                Set<Author> authors = new HashSet<>(
                    authorRepository.findAllById(request.getAuthorIds())
                );
                // setAuthors substitui completamente os autores anteriores
                // o JPA apaga as linhas antigas de book_author e insere as novas
                book.setAuthors(authors);

                // save() dentro do .map() = UPDATE (o book já tem id)
                // devolve o Book atualizado — que fica dentro do Optional
                return bookRepository.save(book);
            });
        // se findById devolveu Optional.empty() → .map() não executa → devolve Optional.empty()
        // o controller converte isso em 404
    }
    /*
     * DELETE BOOK
     */
    public boolean deleteBook(Long id) {

        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }

        return false;
    }
}