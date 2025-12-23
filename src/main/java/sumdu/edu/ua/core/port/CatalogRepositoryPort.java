package sumdu.edu.ua.core.port;

import sumdu.edu.ua.core.domain.Book;
import sumdu.edu.ua.core.domain.Page;
import sumdu.edu.ua.core.domain.PageRequest;

/**
 * Port interface for book catalog repository operations.
 * This follows the Port-Adapter pattern for repository abstraction.
 */
public interface CatalogRepositoryPort {
    Page<Book> search(String query, PageRequest request);
    Book findById(long id);
    Book add(String title, String author, int pubYear);
}

