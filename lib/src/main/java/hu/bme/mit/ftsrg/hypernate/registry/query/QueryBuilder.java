package hu.bme.mit.ftsrg.hypernate.registry.query;

import java.util.List;

/**
 * Fluent query builder API surface for registry rich-queries.
 *
 * Example usage:
 * <pre>
 * List<Asset> results = registry.query(Asset.class)
 *     .where("color").is("blue")
 *     .and("size").greaterThan(10)
 *     .and("owner").in("Alice", "Bob")
 *     .sortBy("value", SortOrder.DESC)
 *     .limit(50)
 *     .execute();
 * </pre>
 */
public interface QueryBuilder<T> {
    FieldConditionBuilder<T> where(String field);

    FieldConditionBuilder<T> and(String field);

    FieldConditionBuilder<T> or(String field);

    QueryBuilder<T> sortBy(String field, SortOrder order);

    QueryBuilder<T> limit(int limit);

    List<T> execute();

    /**
     * Return an immutable representation of the built query for adapters.
     */
    Query toQuery();
}
