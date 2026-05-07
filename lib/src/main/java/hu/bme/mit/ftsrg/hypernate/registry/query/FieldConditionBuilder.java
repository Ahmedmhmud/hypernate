package hu.bme.mit.ftsrg.hypernate.registry.query;

/**
 * Builder returned by {@link QueryBuilder#where(String)}, {@link QueryBuilder#and(String)},
 * and {@link QueryBuilder#or(String)} to specify conditions on a single field.
 *
 * <p>Each terminal condition method (is, greaterThan, in, etc.) returns back to
 * {@link QueryBuilder} to enable method chaining and fluent API continuation.
 *
 * <p>Example:
 * <pre>
 * QueryBuilder<Asset> builder = registry.query(Asset.class)
 *     .where("color").is("blue")
 *     .and("size").greaterThan(10)
 *     .and("owner").in("Alice", "Bob");
 * </pre>
 *
 * @param <T> the entity type
 */
public interface FieldConditionBuilder<T> {
    /**
     * Add an equality condition (field == value).
     *
     * @param value the value to match
     * @return the query builder for chain continuation
     */
    QueryBuilder<T> is(Object value);

    /**
     * Add a greater-than condition (field > value).
     *
     * @param value a numeric value
     * @return the query builder for chain continuation
     */
    QueryBuilder<T> greaterThan(Number value);

    /**
     * Add a less-than condition (field < value).
     *
     * @param value a numeric value
     * @return the query builder for chain continuation
     */
    QueryBuilder<T> lessThan(Number value);

    /**
     * Add an IN condition (field in [value1, value2, ...]).
     *
     * @param values the set of values to match
     * @return the query builder for chain continuation
     */
    QueryBuilder<T> in(Object... values);

    /**
     * Add an EXISTS condition (field exists / is not null).
     *
     * @return the query builder for chain continuation
     */
    QueryBuilder<T> exists();

    /**
     * Add a regex match condition (field matches regex pattern).
     *
     * @param regex the regular expression pattern
     * @return the query builder for chain continuation
     */
    QueryBuilder<T> matchesRegex(String regex);
}

