package com.example.firstspringbootproject.controller.authors;


import com.example.firstspringbootproject.model.Author;
import com.example.firstspringbootproject.service.authors.AuthorService;

import jakarta.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller; // Repara: Controller, não RestController
import org.springframework.ui.Model; // Para passar dados para o template
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/authors")
public class AuthorViewController {

    private final AuthorService authorService; // Dependência do service.

    // Construtor com injeção de dependência do BookService.
    public AuthorViewController( AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public String listAuthors(Model model, Authentication auth) {
        model.addAttribute("authors", authorService.getAllAuthors());
        model.addAttribute("username", auth.getName());
        return "authors/listagem";
    }
    @GetMapping("/new")
    public String showCreateForm(Model model) {

        // author vazio — o Thymeleaf usa-o para fazer bind dos campos do form
        // th:object="${author}" no template liga-se a este objeto
        model.addAttribute("author",new Author() );

        return "authors/create";
    }
    // GUARDAR novo autor: POST /authors
    @PostMapping
    public String createAuthor(@Valid @ModelAttribute("author") Author author,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Se houver erros de validação, volta ao form
            return "authors/create";
        }
        authorService.createAuthor(author);
        // Redirect para a lista após criar
        return "redirect:/authors";
    }

     // MOSTRAR formulário de edição: GET /authors/{id}/edit
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        return authorService.getAuthorById(id)
                .map(b -> {
                    model.addAttribute("author", b);
                    return "authors/edit";
                })
                .orElse("redirect:/authors"); // se não encontrar, volta à lista
    }

    // ATUALIZAR autor: POST /authors/{id}
    @PostMapping("/{id}")
    public String updateAuthor(@PathVariable Long id,
                             @Valid @ModelAttribute("author") Author author,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "authors/edit";
        }
        authorService.updateAuthor(id, author);
        return "redirect:/authors";
    }

    // APAGAR livro: GET /authors/{id}/delete (simples)
    @GetMapping("/{id}/delete")
    public String deleteBook(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return "redirect:/authors";
    }

}
