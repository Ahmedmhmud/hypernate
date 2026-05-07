package hu.bme.mit.ftsrg.hypernate.registry.query;

/**
 * Immutable descriptor for a sort clause.
 *
 * <p>Produced by {@link QueryBuilder#sortBy(String, SortOrder)} and collected
 * in the {@link Query} object. Adapters use this to generate provider-specific
 * ORDER BY clauses.
 *
 * @see Query
 */
public final class Sort {
    private final String field;
    private final SortOrder order;

    /**
     * Construct a sort descriptor.
     *
     * @param field the field to sort by
     * @param order the sort order (ASC or DESC)
     */
    public Sort(String field, SortOrder order) {
        this.field = field;
        this.order = order;
    }

    /**
     * Get the field name.
     *
     * @return the field name
     */
    public String getField() { return field; }

    /**
     * Get the sort order.
     *
     * @return the sort order
     */
    public SortOrder getOrder() { return order; }
}

