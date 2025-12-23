package sumdu.edu.ua.core.port;

import sumdu.edu.ua.core.domain.Comment;
import sumdu.edu.ua.core.domain.Page;
import sumdu.edu.ua.core.domain.PageRequest;

import java.time.Instant;

/**
 * Port interface for comment repository operations.
 * This follows the Port-Adapter pattern for repository abstraction.
 */
public interface CommentRepositoryPort {
    void add(long bookId, String author, String text);
    Page<Comment> list(long bookId, String author, Instant since, PageRequest request);
    Comment findById(long bookId, long commentId);
    void delete(long bookId, long commentId);
}

