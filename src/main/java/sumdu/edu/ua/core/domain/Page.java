package sumdu.edu.ua.core.domain;

import java.util.List;

/**
 * Represents a paginated result set.
 */
public class Page<T> {
    private final List<T> items;
    private final PageRequest request;
    private final long total;

    public Page(List<T> items, PageRequest request, long total) {
        this.items = items;
        this.request = request;
        this.total = total;
    }

    public List<T> getItems() {
        return items;
    }

    public PageRequest getRequest() {
        return request;
    }

    public long getTotal() {
        return total;
    }
}

