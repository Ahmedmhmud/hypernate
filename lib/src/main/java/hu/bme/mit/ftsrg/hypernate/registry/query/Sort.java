package hu.bme.mit.ftsrg.hypernate.registry.query;

/**
 * Simple sort descriptor.
 */
public final class Sort {
    private final String field;
    private final SortOrder order;

    public Sort(String field, SortOrder order) {
        this.field = field;
        this.order = order;
    }

    public String getField() { return field; }

    public SortOrder getOrder() { return order; }
}
