package hu.bme.mit.ftsrg.hypernate.registry.query;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Immutable representation of a built query.
 *
 * <p>A {@code Query} object is produced by {@link QueryBuilder#toQuery()} and contains:
 * <ul>
 *   <li>An immutable list of {@link Condition} objects representing WHERE clauses</li>
 *   <li>An immutable list of {@link Sort} objects representing ORDER BY clauses</li>
 *   <li>An optional limit for result set size</li>
 * </ul>
 *
 * <p>This object is designed to be consumed by adapters that translate it to
 * provider-specific query languages (CouchDB Mango selectors, SQL WHERE clauses, etc.).
 *
 * <p>Example adaptation to CouchDB:
 * <pre>
 * Query q = registry.query(Asset.class)
 *     .where("color").is("blue")
 *     .and("size").greaterThan(10)
 *     .toQuery();
 *
 * // An adapter might translate this to:
 * // {"selector": {"color": "blue", "size": {"$gt": 10}}}
 * </pre>
 *
 * @see Condition
 * @see Sort
 */
public final class Query {
    private final List<Condition> conditions;
    private final List<Sort> sorts;
    private final Integer limit;

    /**
     * Construct a Query with conditions, sorts, and an optional limit.
     *
     * @param conditions a list of conditions (must not be null)
     * @param sorts a list of sorts (null is converted to empty list)
     * @param limit an optional result limit (may be null)
     */
    public Query(List<Condition> conditions, List<Sort> sorts, Integer limit) {
        this.conditions = Collections.unmodifiableList(Objects.requireNonNull(conditions));
        this.sorts = sorts == null ? Collections.emptyList() : Collections.unmodifiableList(sorts);
        this.limit = limit;
    }

    /**
     * Get the conditions (immutable).
     *
     * @return an immutable list of conditions
     */
    public List<Condition> getConditions() { return conditions; }

    /**
     * Get the sorts (immutable).
     *
     * @return an immutable list of sorts
     */
    public List<Sort> getSorts() { return sorts; }

    /**
     * Get the result limit.
     *
     * @return the limit, or null if no limit is set
     */
    public Integer getLimit() { return limit; }
}

