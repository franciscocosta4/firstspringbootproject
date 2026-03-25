package com.example.firstspringbootproject.controller.books;

import com.example.firstspringbootproject.dto.books.BookRequest;
import com.example.firstspringbootproject.model.Author;
import com.example.firstspringbootproject.service.books.BookService;
import com.example.firstspringbootproject.service.authors.AuthorService;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller; // Repara: Controller, não RestController
import org.springframework.ui.Model; // Para passar dados para o template
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/books")
public class BookViewController {

    private final BookService bookService; // Dependência do service.
    private final AuthorService authorService; // Dependência do service.

    // Construtor com injeção de dependência do BookService.
    public BookViewController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping
    public String listBooks(Model model, Authentication auth) {
        model.addAttribute("books", bookService.getAllBooks());
        model.addAttribute("username", auth.getName());
        return "books/listagem";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {

        // BookRequest vazio — o Thymeleaf usa-o para fazer bind dos campos do form
        // th:object="${bookRequest}" no template liga-se a este objeto
        model.addAttribute("bookRequest", new BookRequest());

        // lista de todos os autores para preencher o <select> no form
        model.addAttribute("authors", authorService.getAllAuthors());

        // bookId null = estamos a criar, não a editar
        // o template usa: th:text="${bookId} == null ? 'Novo Livro' : 'Editar Livro'"
        model.addAttribute("bookId", null);

        return "books/form";
    }

    // GUARDAR novo livro: POST /books
    @PostMapping
      // @ModelAttribute = o Spring lê os campos do form submetido e preenche o BookRequest
    // funciona como @RequestBody mas para forms HTML (application/x-www-form-urlencoded)
    // em vez de JSON (application/json)
    public String createBook(@ModelAttribute BookRequest bookRequest) {

        bookService.createBook(bookRequest);

        // "redirect:/books" instrui o browser a fazer um novo GET /books
        // padrão POST → redirect → GET (evita resubmissão do form ao fazer F5)
        return "redirect:/books";
    }

    // MOSTRAR formulário de edição: GET /books/{id}/edit
    @GetMapping("/{id}/edit")
    // @PathVariable extrai o {id} do URL e injeta no parâmetro Long id
    // GET /books/5/edit → id = 5
    public String showEditForm(@PathVariable Long id, Model model) {
        // getBookById devolve Optional<Book>
        // .map() só executa se o livro existir
        return bookService.getBookById(id)
            .map(book -> {
                // book é o Book encontrado na BD
                // precisamos de converter para BookRequest para pré-preencher o form
                // não podemos passar o Book diretamente porque o form usa authorIds (List<Long>)
                // e o Book tem authors (Set<Author>)
                BookRequest req = new BookRequest();
                req.setTitle(book.getTitle());
                req.setYear(book.getYear());

                // stream() = abre um pipeline de operações sobre a coleção
                // .map(Author::getId) = para cada Author, extrai o getId()
                //   Author::getId é uma method reference = author -> author.getId()
                // .collect(Collectors.toList()) = junta os resultados numa List<Long>
                req.setAuthorIds(
                    book.getAuthors().stream()
                        .map(Author::getId)
                        .collect(Collectors.toList())
                );

                model.addAttribute("bookRequest", req);
                model.addAttribute("authors", authorService.getAllAuthors());

                // bookId com valor = o template sabe que é edição
                // usado em: th:action="${bookId == null} ? @{/books} : @{/books/{id}(id=${bookId})}"
                model.addAttribute("bookId", id);

                return "books/form";
            })
            // .orElse() — se o Optional estiver vazio (livro não existe)
            // redireciona para a lista em vez de mostrar erro
            .orElse("redirect:/books");
    }


    // ATUALIZAR livro: POST /books/{id}
    // nota: usamos POST em vez de PUT porque forms HTML só suportam GET e POST
    // em APIs REST usaríamos @PutMapping, mas em controllers de views é @PostMapping
    @PostMapping("/{id}")
    public String updateBook(
            @PathVariable Long id,
            @ModelAttribute BookRequest bookRequest) {

        // o service devolve Optional<Book> mas aqui não precisamos do resultado
        // só nos interessa redirecionar após a operação
        bookService.updateBook(id, bookRequest);
        return "redirect:/books";
    }

    // APAGAR livro: GET /books/{id}/delete (simples)
    @GetMapping("/{id}/delete")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }
}
