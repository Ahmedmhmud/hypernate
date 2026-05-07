package hu.bme.mit.ftsrg.hypernate.registry.query.impl;

import hu.bme.mit.ftsrg.hypernate.registry.query.*;
import java.util.*;

/**
 * Simple in-memory implementation of QueryBuilder for demonstration and testing.
 * This implementation collects conditions and sorts, and delegates execute() to
 * an executor callback for actual data retrieval.
 */
public class SimpleQueryBuilder<T> implements QueryBuilder<T>, FieldConditionBuilder<T> {

    private final Class<T> entityClass;
    private final List<Condition> conditions;
    private final List<Sort> sorts;
    private Integer limit;
    private String currentField;
    private QueryExecutor<T> executor;

    /**
     * Constructor. Callers are expected to set an executor via
     * {@link #setExecutor(QueryExecutor)}.
     */
    public SimpleQueryBuilder(Class<T> entityClass) {
        this.entityClass = entityClass;
        this.conditions = new ArrayList<>();
        this.sorts = new ArrayList<>();
    }

    /**
     * Set the executor that will handle the actual query execution.
     * This allows Registry to inject a callback for ledger operations.
     */
    public void setExecutor(QueryExecutor<T> executor) {
        this.executor = executor;
    }

    @Override
    public FieldConditionBuilder<T> where(String field) {
        this.currentField = field;
        return this;
    }

    @Override
    public FieldConditionBuilder<T> and(String field) {
        this.currentField = field;
        return this;
    }

    @Override
    public FieldConditionBuilder<T> or(String field) {
        // For now, treat OR the same as AND (sequential conditions).
        // Future: implement proper OR semantics with a ConditionGroup.
        this.currentField = field;
        return this;
    }

    @Override
    public QueryBuilder<T> is(Object value) {
        if (currentField == null) {
            throw new IllegalStateException("No field set for condition");
        }
        conditions.add(new Condition(currentField, Operator.EQ, value));
        return this;
    }

    @Override
    public QueryBuilder<T> greaterThan(Number value) {
        if (currentField == null) {
            throw new IllegalStateException("No field set for condition");
        }
        conditions.add(new Condition(currentField, Operator.GT, value));
        return this;
    }

    @Override
    public QueryBuilder<T> lessThan(Number value) {
        if (currentField == null) {
            throw new IllegalStateException("No field set for condition");
        }
        conditions.add(new Condition(currentField, Operator.LT, value));
        return this;
    }

    @Override
    public QueryBuilder<T> in(Object... values) {
        if (currentField == null) {
            throw new IllegalStateException("No field set for condition");
        }
        conditions.add(new Condition(currentField, Operator.IN, values));
        return this;
    }

    @Override
    public QueryBuilder<T> exists() {
        if (currentField == null) {
            throw new IllegalStateException("No field set for condition");
        }
        conditions.add(new Condition(currentField, Operator.EXISTS));
        return this;
    }

    @Override
    public QueryBuilder<T> matchesRegex(String regex) {
        if (currentField == null) {
            throw new IllegalStateException("No field set for condition");
        }
        conditions.add(new Condition(currentField, Operator.REGEX, regex));
        return this;
    }

    @Override
    public QueryBuilder<T> sortBy(String field, SortOrder order) {
        sorts.add(new Sort(field, order));
        return this;
    }

    @Override
    public QueryBuilder<T> limit(int limit) {
        this.limit = limit;
        return this;
    }

    @Override
    public List<T> execute() {
        if (executor == null) {
            throw new UnsupportedOperationException(
                "Execute not supported: no executor set on this QueryBuilder");
        }
        Query query = toQuery();
        return executor.execute(query);
    }

    @Override
    public Query toQuery() {
        return new Query(new ArrayList<>(conditions), new ArrayList<>(sorts), limit);
    }

    /**
     * Functional interface for executing queries. Registry provides an implementation.
     */
    public interface QueryExecutor<T> {
        List<T> execute(Query query);
    }
}
