package hu.bme.mit.ftsrg.hypernate.registry.query;

/**
 * Enumeration of sort orders.
 *
 * <p>Used by {@link QueryBuilder#sortBy(String, SortOrder)} to specify
 * ascending or descending order.
 */
public enum SortOrder {
    /** Ascending order (A to Z, 0 to 9). */
    ASC,

    /** Descending order (Z to A, 9 to 0). */
    DESC
}

