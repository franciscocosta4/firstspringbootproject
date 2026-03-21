package com.example.firstspringbootproject.controller;


import com.example.firstspringbootproject.model.Book;
import com.example.firstspringbootproject.service.BookService;
import org.springframework.stereotype.Controller;        // Repara: Controller, não RestController
import org.springframework.ui.Model;               // Para passar dados para o template
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/books")
public class BookViewController {

    private final BookService bookService; // Dependência do service.

    // Construtor com injeção de dependência do BookService.
    public BookViewController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "listagem";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("book", new Book());
        return "form";
    }

        // GUARDAR novo livro: POST /books
    @PostMapping
    public String createBook(@Valid @ModelAttribute("book") Book book,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Se houver erros de validação, volta ao form
            return "form";
        }
        bookService.createBook(book);
        // Redirect para a lista após criar
        return "redirect:/books";
    }

    // MOSTRAR formulário de edição: GET /books/{id}/edit
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        return bookService.getBookById(id)
                .map(b -> {
                    model.addAttribute("book", b);
                    return "form";
                })
                .orElse("redirect:/books"); // se não encontrar, volta à lista
    }

    // ATUALIZAR livro: POST /books/{id}
    @PostMapping("/{id}")
    public String updateBook(@PathVariable Long id,
                             @Valid @ModelAttribute("book") Book book,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "form";
        }
        bookService.updateBook(id, book);
        return "redirect:/books";
    }

    // APAGAR livro: GET /books/{id}/delete (simples)
    @GetMapping("/{id}/delete")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }
}
