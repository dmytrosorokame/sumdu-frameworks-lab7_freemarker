package sumdu.edu.ua.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sumdu.edu.ua.core.domain.Comment;
import sumdu.edu.ua.core.port.CommentRepositoryPort;

import java.time.Duration;
import java.time.Instant;

/**
 * Service class for comment business logic.
 * 
 * Business rules:
 * - A comment can be deleted only within 24 hours from its creation.
 * - Comment fields (author, text) must not be empty.
 * - Book ID must be valid (greater than 0).
 */
@Service
public class CommentService {
    private final CommentRepositoryPort repo;

    @Autowired
    public CommentService(CommentRepositoryPort repo) {
        this.repo = repo;
    }

    /**
     * Validates comment fields according to business rules.
     *
     * @param bookId the book ID
     * @param author the comment author
     * @param text the comment text
     * @throws IllegalArgumentException if validation fails
     */
    public void validateCommentFields(long bookId, String author, String text) {
        if (bookId <= 0) {
            throw new IllegalArgumentException("bookId must be greater than 0");
        }
        if (author == null || author.isBlank()) {
            throw new IllegalArgumentException("author is required");
        }
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("text is required");
        }
    }

    /**
     * Adds a new comment after validation.
     *
     * @param bookId the book ID
     * @param author the comment author
     * @param text the comment text
     * @throws IllegalArgumentException if validation fails
     */
    public void addComment(long bookId, String author, String text) {
        validateCommentFields(bookId, author, text);
        repo.add(bookId, author.trim(), text.trim());
    }

    /**
     * Deletes a comment only if it was created not more than 24 hours ago.
     *
     * @param bookId the book ID
     * @param commentId the comment ID
     * @throws IllegalStateException if the comment was not found or is older than 24 hours
     */
    public void delete(long bookId, long commentId) {
        Comment comment = repo.findById(bookId, commentId);
        if (comment == null) {
            throw new IllegalStateException("The comment was not found");
        }

        Instant createdAt = comment.getCreatedAt();
        if (createdAt == null) {
            throw new IllegalStateException("The time of creation of the comment is unknown");
        }

        if (Duration.between(createdAt, Instant.now()).toHours() > 24) {
            throw new IllegalStateException("You can't delete a comment that is older than 24 hours");
        }

        repo.delete(bookId, commentId);
    }
}
