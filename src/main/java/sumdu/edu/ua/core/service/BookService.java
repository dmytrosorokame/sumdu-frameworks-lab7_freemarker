package sumdu.edu.ua.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sumdu.edu.ua.core.domain.Book;
import sumdu.edu.ua.core.port.CatalogRepositoryPort;

/**
 * Service class for book business logic.
 * 
 * Business rules:
 * - Book title and author must not be empty.
 * - Publication year must be valid (greater than 0).
 * - Book ID must be valid (greater than 0) for lookups.
 */
@Service
public class BookService {
    private final CatalogRepositoryPort bookRepo;

    @Autowired
    public BookService(CatalogRepositoryPort bookRepo) {
        this.bookRepo = bookRepo;
    }

    /**
     * Validates book fields according to business rules.
     *
     * @param title the book title
     * @param author the book author
     * @param pubYear the publication year
     * @throws IllegalArgumentException if validation fails
     */
    public void validateBookFields(String title, String author, int pubYear) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("title is required");
        }
        if (author == null || author.isBlank()) {
            throw new IllegalArgumentException("author is required");
        }
        if (pubYear <= 0) {
            throw new IllegalArgumentException("pubYear must be greater than 0");
        }
    }

    /**
     * Validates book ID.
     *
     * @param id the book ID
     * @throws IllegalArgumentException if validation fails
     */
    public void validateBookId(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("bookId must be greater than 0");
        }
    }

    /**
     * Adds a new book after validation.
     *
     * @param title the book title
     * @param author the book author
     * @param pubYear the publication year
     * @return the created book
     * @throws IllegalArgumentException if validation fails
     */
    public Book addBook(String title, String author, int pubYear) {
        validateBookFields(title, author, pubYear);
        return bookRepo.add(title.trim(), author.trim(), pubYear);
    }

    /**
     * Finds a book by ID with validation.
     *
     * @param id the book ID
     * @return the book, or null if not found
     * @throws IllegalArgumentException if bookId is invalid
     */
    public Book findById(long id) {
        validateBookId(id);
        return bookRepo.findById(id);
    }
}

