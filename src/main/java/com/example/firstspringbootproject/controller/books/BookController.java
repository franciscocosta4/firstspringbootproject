package com.example.firstspringbootproject.controller.books; // Pacote dos controladores REST.

import com.example.firstspringbootproject.dto.books.BookRequest;
import com.example.firstspringbootproject.model.Book;
import com.example.firstspringbootproject.service.books.BookService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// @RestController = @Controller + @ResponseBody
// significa que todos os métodos devolvem dados (JSON) diretamente no body da resposta
// em vez de nomes de templates Thymeleaf
@RestController

// @RequestMapping define o prefixo de URL para todos os métodos desta classe
// todos os endpoints aqui dentro começam com /api/books
@RequestMapping("/api/books")
public class BookController {

    // final = a referência não pode ser reatribuída após o construtor
    // boa prática: garante que o service não é substituído acidentalmente
    private final BookService bookService;

    // injeção de dependência pelo construtor
    // o Spring vê que BookApiController precisa de BookService
    // e injeta automaticamente o @Bean correspondente
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // ─────────────────────────────────────────────
    // GET ALL — GET /api/books
    // ─────────────────────────────────────────────

    // @GetMapping sem parâmetro = responde a GET /api/books (o prefixo da classe)
    @GetMapping
    // ResponseEntity<T> = resposta HTTP completa: status + headers + body
    // ResponseEntity<List<Book>> = body é uma lista de Books, status será 200
    public ResponseEntity<List<Book>> getAll() {
        List<Book> books = bookService.getAllBooks();
        // ResponseEntity.ok() = status 200 OK com o objeto no body
        return ResponseEntity.ok(books);
    }

    // ─────────────────────────────────────────────
    // GET BY ID — GET /api/books/{id}
    // ─────────────────────────────────────────────

    // @GetMapping("/{id}") = responde a GET /api/books/1, /api/books/42, etc.
    // {id} é uma variável de path — o valor é capturado e injetado no método
    @GetMapping("/{id}")
    public ResponseEntity<Book> getById(
            // @PathVariable liga o {id} do URL ao parâmetro Long id
            @PathVariable Long id) {

        return bookService.getBookById(id)
                // Optional.map() — se o Optional tiver valor, aplica a função
                // ResponseEntity::ok é uma method reference = book -> ResponseEntity.ok(book)
                .map(ResponseEntity::ok)
                // se o Optional estiver vazio (livro não existe) → 404 Not Found
                .orElse(ResponseEntity.notFound().build());
    }

    // ─────────────────────────────────────────────
    // CREATE — POST /api/books
    // ─────────────────────────────────────────────

    // @PostMapping = responde a pedidos HTTP POST
    @PostMapping
    public ResponseEntity<Book> create(
            // @RequestBody = o Spring lê o JSON do body do pedido e converte para BookRequest
            // exemplo de JSON: { "title": "Clean Code", "year": 2008, "authorIds": [1, 2] }
            @RequestBody BookRequest request) {

        Book saved = bookService.createBook(request);

        // new ResponseEntity<>(body, status)
        // HttpStatus.CREATED = 201 — convenção REST para recursos criados com sucesso
        // 200 seria tecnicamente errado para criação
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // ─────────────────────────────────────────────
    // UPDATE — PUT /api/books/{id}
    // ─────────────────────────────────────────────

    // @PutMapping = responde a HTTP PUT — substituição completa do recurso
    @PutMapping("/{id}")
    public ResponseEntity<Book> update(
            @PathVariable Long id,
            @RequestBody BookRequest request) {

        // bookService.updateBook devolve Optional<Book>
        // → Optional.map() transforma Book em ResponseEntity.ok(book) se existir
        // → .orElse() devolve 404 se o Optional estiver vazio
        return bookService.updateBook(id, request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ─────────────────────────────────────────────
    // DELETE — DELETE /api/books/{id}
    // ─────────────────────────────────────────────

    // @DeleteMapping = responde a HTTP DELETE
    @DeleteMapping("/{id}")
    // ResponseEntity<?> — o ? (wildcard) significa que o body pode ser qualquer tipo
    // usamos porque tanto devolvemos um Book (ou mensagem) como um body vazio (204)
    public ResponseEntity<?> delete(@PathVariable Long id) {

        boolean deleted = bookService.deleteBook(id);

        if (deleted) {
            // 204 No Content — sucesso mas sem body na resposta
            // convenção REST para DELETE bem sucedido
            return ResponseEntity.noContent().build();
        }

        // 404 se o livro não existia
        return ResponseEntity.notFound().build();
    }
}
