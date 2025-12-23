package sumdu.edu.ua.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sumdu.edu.ua.core.domain.Book;
import sumdu.edu.ua.core.domain.Comment;
import sumdu.edu.ua.core.domain.Page;
import sumdu.edu.ua.core.domain.PageRequest;
import sumdu.edu.ua.core.port.CommentRepositoryPort;
import sumdu.edu.ua.core.service.BookService;
import sumdu.edu.ua.core.service.CommentService;
import sumdu.edu.ua.web.http.ErrorResponse;

@Controller
@RequestMapping("/books/{bookId}")
public class CommentsController {

    private static final Logger log = LoggerFactory.getLogger(CommentsController.class);

    @Autowired
    private BookService bookService;

    @Autowired
    private CommentRepositoryPort commentRepo;

    @Autowired
    private CommentService commentService;

    /**
     * Handles GET /books/{bookId} request and returns Thymeleaf view.
     */
    @GetMapping
    public String showBookWithComments(
            @PathVariable long bookId,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "20") int size,
            Model model) {

        Book book = bookService.findById(bookId);
        if (book == null) {
            log.warn("Book not found: {}", bookId);
            return "redirect:/books";
        }

        if (page < 0) {
            page = 0;
        }
        if (size <= 0 || size > 100) {
            size = 20;
        }

        PageRequest pageRequest = new PageRequest(page, size);
        Page<Comment> result = commentRepo.list(bookId, null, null, pageRequest);

        long total = result.getTotal();
        int totalPages = (int) ((total + size - 1) / size);

        model.addAttribute("book", book);
        model.addAttribute("comments", result.getItems());
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("total", total);
        model.addAttribute("totalPages", totalPages);

        return "book-comments";
    }

    /**
     * Handles POST /books/{bookId} request for adding comments via web form.
     */
    @PostMapping
    public ResponseEntity<?> addComment(
            @PathVariable long bookId,
            @RequestParam String author,
            @RequestParam String text) {

        try {
            commentService.addComment(bookId, author, text);
        } catch (IllegalArgumentException e) {
            log.warn("Bad request: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Bad Request",
                            e.getMessage(), "/books/" + bookId));
        } catch (Exception e) {
            log.error("Cannot save comment", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Internal Server Error", "Cannot save comment", "/books/" + bookId));
        }

        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", "/books/" + bookId)
                .build();
    }

    /**
     * Handles POST /books/{bookId} with _method=delete for deleting comments via web form.
     */
    @PostMapping(params = "_method=delete")
    public ResponseEntity<?> deleteComment(
            @PathVariable long bookId,
            @RequestParam long commentId) {

        try {
            commentService.delete(bookId, commentId);
        } catch (IllegalStateException e) {
            log.warn("Cannot delete comment due to business rule: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponse(HttpStatus.CONFLICT.value(), "Conflict",
                            e.getMessage(), "/books/" + bookId));
        } catch (Exception e) {
            log.error("Cannot delete comment", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Internal Server Error", "Cannot delete comment", "/books/" + bookId));
        }

        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", "/books/" + bookId)
                .build();
    }
}
