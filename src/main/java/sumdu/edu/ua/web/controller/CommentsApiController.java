package sumdu.edu.ua.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sumdu.edu.ua.core.port.CatalogRepositoryPort;
import sumdu.edu.ua.core.service.BookService;
import sumdu.edu.ua.core.service.CommentService;
import sumdu.edu.ua.web.http.CommentRequest;
import sumdu.edu.ua.web.http.ErrorResponse;

@RestController
@RequestMapping("/comments")
public class CommentsApiController {

    private static final Logger log = LoggerFactory.getLogger(CommentsApiController.class);

    private final BookService bookService;
    private final CommentService commentService;

    @Autowired
    public CommentsApiController(BookService bookService,
                                 CommentService commentService) {
        this.bookService = bookService;
        this.commentService = commentService;
    }

    /**
     * POST /comments - Adds a new comment to a book.
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addComment(@RequestBody CommentRequest request) {
        try {
            var book = bookService.findById(request.getBookId());
            if (book == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Not Found",
                                "Book not found with id: " + request.getBookId(), "/comments"));
            }

            commentService.addComment(request.getBookId(), request.getAuthor(), request.getText());

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new CommentRequest(request.getBookId(), request.getAuthor(), request.getText()));

        } catch (IllegalArgumentException e) {
            log.warn("Bad request: {}", e.getMessage());
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Bad Request",
                            e.getMessage(), "/comments"));
        } catch (Exception e) {
            log.error("DB error while POST /comments", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "Internal Server Error", "DB error", "/comments"));
        }
    }
}
