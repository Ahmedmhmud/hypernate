package hu.bme.mit.ftsrg.hypernate.registry.query;

import java.util.List;

/**
 * Fluent query builder API surface for registry rich-queries.
 *
 * <p>This interface defines a type-safe, fluent API for constructing queries programmatically.
 * Implementations collect conditions, sorts, and limits into an immutable {@link Query} object,
 * which can be executed or translated by adapters to provider-specific formats (CouchDB,
 * SQL, etc.).
 *
 * <p>Example usage:
 * <pre>
 * List<Asset> results = registry.query(Asset.class)
 *     .where("color").is("blue")
 *     .and("size").greaterThan(10)
 *     .and("owner").in("Alice", "Bob")
 *     .sortBy("value", SortOrder.DESC)
 *     .limit(50)
 *     .execute();
 * </pre>
 *
 * <p>The fluent surface is split into two interfaces: {@code QueryBuilder} provides the main
 * control flow (and/or, sorting, limits, execution), while {@code FieldConditionBuilder}
 * provides field-specific operators (is, greaterThan, in, etc.).
 *
 * @param <T> the entity type being queried
 */
public interface QueryBuilder<T> {
    /**
     * Start building a condition on the given field.
     *
     * @param field the field name
     * @return a condition builder for this field
     */
    FieldConditionBuilder<T> where(String field);

    /**
     * Add an AND condition on the given field.
     *
     * @param field the field name
     * @return a condition builder for this field
     */
    FieldConditionBuilder<T> and(String field);

    /**
     * Add an OR condition on the given field.
     * (Currently treated as AND; future versions may support disjunctive logic.)
     *
     * @param field the field name
     * @return a condition builder for this field
     */
    FieldConditionBuilder<T> or(String field);

    /**
     * Add a sort clause.
     *
     * @param field the field to sort by
     * @param order the sort order (ASC or DESC)
     * @return this builder for chaining
     */
    QueryBuilder<T> sortBy(String field, SortOrder order);

    /**
     * Set a result limit.
     *
     * @param limit the maximum number of results to return
     * @return this builder for chaining
     */
    QueryBuilder<T> limit(int limit);

    /**
     * Execute the query and return results of the requested entity type.
     *
     * @return a list of entities matching the query
     * @throws UnsupportedOperationException if no executor is available or if
     *         the underlying adapter does not support this operation
     */
    List<T> execute();

    /**
     * Return an immutable representation of the built query suitable for
     * serialization or for adapters to translate to provider-specific formats.
     *
     * @return an immutable Query object
     */
    Query toQuery();
}

