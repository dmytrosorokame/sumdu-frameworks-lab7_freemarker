package sumdu.edu.ua.web.http;

/**
 * DTO for comment creation request.
 */
public class CommentRequest {
    private long bookId;
    private String author;
    private String text;

    public CommentRequest() {
    }

    public CommentRequest(long bookId, String author, String text) {
        this.bookId = bookId;
        this.author = author;
        this.text = text;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

