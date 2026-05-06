package hu.bme.mit.ftsrg.hypernate.registry.query;

/**
 * Builder returned by {@link QueryBuilder#where(String)} / {@link QueryBuilder#and(String)}
 * to specify a condition on a single field. Each terminal condition method returns
 * back to {@link QueryBuilder} so fluent chaining can continue.
 */
public interface FieldConditionBuilder<T> {
    QueryBuilder<T> is(Object value);

    QueryBuilder<T> greaterThan(Number value);

    QueryBuilder<T> lessThan(Number value);

    QueryBuilder<T> in(Object... values);

    QueryBuilder<T> exists();

    QueryBuilder<T> matchesRegex(String regex);
}
