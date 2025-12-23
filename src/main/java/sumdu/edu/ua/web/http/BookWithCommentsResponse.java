package sumdu.edu.ua.web.http;

import sumdu.edu.ua.core.domain.Book;
import sumdu.edu.ua.core.domain.Comment;

import java.util.List;

/**
 * DTO for book with comments response.
 */
public class BookWithCommentsResponse {
    private final Book book;
    private final List<Comment> comments;

    public BookWithCommentsResponse(Book book, List<Comment> comments) {
        this.book = book;
        this.comments = comments;
    }

    public Book getBook() {
        return book;
    }

    public List<Comment> getComments() {
        return comments;
    }
}
