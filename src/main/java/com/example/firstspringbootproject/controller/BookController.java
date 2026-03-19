package com.example.firstspringbootproject.controller;  // Pacote dos controladores REST.

import com.example.firstspringbootproject.model.Book;
import com.example.firstspringbootproject.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController  // Indica que esta classe expõe endpoints REST (JSON).
@RequestMapping("/api/books")  // Prefixo comum para todos os endpoints deste controller.
public class BookController {

    private final BookService bookService;  // Dependência do service.

    // Construtor com injeção de dependência do BookService.
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // CREATE: POST /api/books
    @PostMapping  // Mapeia pedidos HTTP POST para este método.
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        /*
          @RequestBody diz ao Spring para converter o JSON do body em objeto Book.
          Exemplo de JSON enviado pelo cliente:
          {
            "title": "Clean Code",
            "author": "Robert C. Martin",
            "year": 2008
          }
        */
        Book savedBook = bookService.createBook(book);  // Chama o service para criar.
        // ResponseEntity permite controlar o status HTTP e o body.
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);  // 201 Created.
    }

    // READ: GET /api/books
    @GetMapping  // Mapeia HTTP GET (sem id) para listar todos os livros.
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();  // Vai ao service buscar todos.
        return new ResponseEntity<>(books, HttpStatus.OK);  // 200 OK com lista no body.
    }

    // READ: GET /api/books/{id}
    @GetMapping("/{id}")  // Mapeia GET com um parâmetro de path (id).
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        /*
          @PathVariable indica que o valor {id} da URL vai ser passado como argumento.
          Ex: GET /api/books/1 -> id = 1
        */
        Optional<Book> book = bookService.getBookById(id);

        // Se o livro existir, devolvemos 200 com o body.
        return book
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                // Se não existir, devolvemos 404.
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // UPDATE: PUT /api/books/{id}
    @PutMapping("/{id}")  // Mapeia HTTP PUT para atualizar um recurso existente.
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        /*
          Recebe o id na URL e os novos dados no body em JSON.
          Ex:
          PUT /api/books/1
          {
            "title": "Clean Code (2nd Edition)",
            "author": "Robert C. Martin",
            "year": 2010
          }
        */
        Optional<Book> updatedBook = bookService.updateBook(id, book);

        return updatedBook
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK)) // 200 se atualizou.
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); // 404 se não existe.
    }

    // DELETE: DELETE /api/books/{id}
    @DeleteMapping("/{id}")  // Mapeia HTTP DELETE para apagar um recurso.
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        boolean deleted = bookService.deleteBook(id);  // Tenta apagar.

        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204, sem body.
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // 404 se não encontrou.
        }
    }
}
