package sumdu.edu.ua.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sumdu.edu.ua.core.domain.Book;
import sumdu.edu.ua.core.domain.Page;
import sumdu.edu.ua.core.domain.PageRequest;
import sumdu.edu.ua.core.port.CatalogRepositoryPort;
import sumdu.edu.ua.core.service.BookService;
import sumdu.edu.ua.web.service.MailService;

@Controller
public class BooksController {

    private final CatalogRepositoryPort bookRepo;
    private final BookService bookService;
    private final MailService mailService;

    @Autowired
    public BooksController(CatalogRepositoryPort bookRepo, BookService bookService, MailService mailService) {
        this.bookRepo = bookRepo;
        this.bookService = bookService;
        this.mailService = mailService;
    }

    /**
     * Handles GET /books request and returns Thymeleaf view.
     */
    @GetMapping("/books")
    public String listBooks(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "20") int size,
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String sort,
            Model model) {

        if (page < 0) {
            page = 0;
        }
        if (size <= 0 || size > 100) {
            size = 20;
        }

        PageRequest pageRequest = new PageRequest(page, size, sort, true);
        Page<Book> result = bookRepo.search(q, pageRequest);
        long total = result.getTotal();
        int totalPages = (int) ((total + size - 1) / size);

        model.addAttribute("books", result.getItems());
        model.addAttribute("q", q);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("sort", sort);
        model.addAttribute("total", total);
        model.addAttribute("totalPages", totalPages);

        return "books";
    }

    /**
     * Handles GET /books/add request and returns form view.
     */
    @GetMapping("/books/add")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "book-form";
    }

    /**
     * Handles POST /books/add request and creates a new book.
     */
    @PostMapping("/books/add")
    public String addBook(@ModelAttribute Book book, RedirectAttributes redirectAttributes) {
        try {
            Book saved = bookService.addBook(
                    book.getTitle(),
                    book.getAuthor(),
                    book.getPubYear()
            );
            try {
                mailService.sendNewBookEmail(saved);
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("emailError", "Failed to send notification email, but book was added successfully");
            }
            redirectAttributes.addFlashAttribute("successMessage", "book.added.success");
            return "redirect:/books";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            redirectAttributes.addFlashAttribute("book", book);
            return "redirect:/books/add";
        }
    }
}
