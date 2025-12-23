package sumdu.edu.ua.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sumdu.edu.ua.core.domain.Book;
import sumdu.edu.ua.core.domain.PageRequest;
import sumdu.edu.ua.core.port.CatalogRepositoryPort;
import sumdu.edu.ua.web.http.ErrorResponse;

@RestController
@RequestMapping("/api/books")
public class BooksApiController {

    private static final Logger log = LoggerFactory.getLogger(BooksApiController.class);

    private final sumdu.edu.ua.core.service.BookService bookService;
    private final CatalogRepositoryPort bookRepo;
    private final sumdu.edu.ua.core.port.CommentRepositoryPort commentRepo;

    @Autowired
    public BooksApiController(sumdu.edu.ua.core.service.BookService bookService,
                              CatalogRepositoryPort bookRepo,
                              sumdu.edu.ua.core.port.CommentRepositoryPort commentRepo) {
        this.bookService = bookService;
        this.bookRepo = bookRepo;
        this.commentRepo = commentRepo;
    }

    /**
     * GET /api/books - Returns list of books in JSON format.
     * Implements pagination, search, and sorting.
     * 
     * @param page page number (starts from 0, default: 0)
     * @param size page size (1..100, default: 10)
     * @param q optional search query by title/author
     * @param sort sort field (title, author, pub_year)
     * @return ResponseEntity with Page<Book> in JSON format
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getBooks(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String sort) {

        if (page < 0) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Bad Request",
                            "page must be >= 0", "/api/books"));
        }
        if (size <= 0 || size > 100) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Bad Request",
                            "size must be between 1 and 100", "/api/books"));
        }

        try {
            PageRequest pageRequest = new PageRequest(page, size, sort, true);
            var result = bookRepo.search(q, pageRequest);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("DB error while GET /api/books", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Internal Server Error", "DB error", "/api/books"));
        }
    }

    /**
     * GET /api/books/{id} - Returns a single book with comments in JSON format.
     * 
     * @param id book ID
     * @return ResponseEntity with BookWithCommentsResponse in JSON format
     */
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getBookById(@PathVariable long id) {
        try {
            Book book = bookService.findById(id);
            if (book == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Not Found",
                                "Book not found with id: " + id, "/api/books/" + id));
            }

            var pageRequest = new sumdu.edu.ua.core.domain.PageRequest(0, 1000);
            var commentsPage = commentRepo.list(id, null, null, pageRequest);
            
            var response = new sumdu.edu.ua.web.http.BookWithCommentsResponse(
                    book, commentsPage.getItems());
            
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            log.warn("Bad request: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Bad Request",
                            e.getMessage(), "/api/books/" + id));
        } catch (Exception e) {
            log.error("DB error while GET /api/books/{}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Internal Server Error", "DB error", "/api/books/" + id));
        }
    }

    /**
     * POST /api/books - Creates a new book.
     * 
     * @param book book data from request body
     * @return ResponseEntity with created Book in JSON format
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createBook(@RequestBody Book book) {
        try {
            Book saved = bookService.addBook(
                    book.getTitle(),
                    book.getAuthor(),
                    book.getPubYear()
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(saved);

        } catch (IllegalArgumentException e) {
            log.warn("Bad request: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Bad Request",
                            e.getMessage(), "/api/books"));
        } catch (Exception e) {
            log.error("DB error while POST /api/books", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Internal Server Error", "DB error", "/api/books"));
        }
    }

}

