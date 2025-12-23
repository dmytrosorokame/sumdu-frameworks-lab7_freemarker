package sumdu.edu.ua.core.domain;

/**
 * Represents pagination and sorting parameters for queries.
 */
public class PageRequest {
    private final int page;
    private final int size;
    private final String sortBy;
    private final boolean sortDesc;

    public PageRequest(int page, int size) {
        this(page, size, null, true);
    }

    public PageRequest(int page, int size, String sortBy, boolean sortDesc) {
        this.page = page;
        this.size = size;
        this.sortBy = sortBy;
        this.sortDesc = sortDesc;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public String getSortBy() {
        return sortBy;
    }

    public boolean isSortDesc() {
        return sortDesc;
    }
}

