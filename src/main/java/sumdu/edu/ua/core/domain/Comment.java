package sumdu.edu.ua.core.domain;

import java.time.Instant;

/**
 * Domain model representing a comment on a book.
 */
public class Comment {
    private final long id;
    private final long bookId;
    private final String author;
    private final String text;
    private final Instant createdAt;

    public Comment(long id, long bookId, String author, String text, Instant createdAt) {
        this.id = id;
        this.bookId = bookId;
        this.author = author;
        this.text = text;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public long getBookId() {
        return bookId;
    }

    public String getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}

