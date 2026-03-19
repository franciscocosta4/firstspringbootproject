package com.example.firstspringbootproject.service;

import com.example.firstspringbootproject.model.Book;
import com.example.firstspringbootproject.repository.BookRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class BookService {


    private final BookRepository bookRepository; // dependecia do repository

    // Injeção de dependência via construtor (recomendado).
    public BookService(BookRepository bookRepository) { 
        this.bookRepository = bookRepository;  // Spring passa o BookRepository aqui.
    }

    public Book createBook(Book book){
        // save() guarda o livro na BD e devolve o objeto persistido (com id preenchido).
        return bookRepository.save(book);
    }

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id){
         // findById() devolve Optional<Book> para lidar com "pode não existir".
        return bookRepository.findById(id);
    }

    public Optional<Book> updateBook(Long id, Book updatedBook) {
        // Primeiro vemos se o livro existe.
        return bookRepository.findById(id).map(existingBook -> {
            // Atualizamos os campos do livro existente com os do updatedBook.
            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setAuthor(updatedBook.getAuthor());
            existingBook.setYear(updatedBook.getYear());
            // Guardamos novamente na BD.
            return bookRepository.save(existingBook);
        });
        // Se não existir, o Optional fica vazio.
    }

     public boolean deleteBook(Long id) {
        if (bookRepository.existsById(id)) {  // Verifica se o id existe na BD.
            bookRepository.deleteById(id);    // Apaga o registo.
            return true;                      // Indica sucesso.
        }
        return false;                         // Indica que não havia registo.
    }

}
