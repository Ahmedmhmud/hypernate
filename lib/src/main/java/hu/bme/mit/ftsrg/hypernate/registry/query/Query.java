package hu.bme.mit.ftsrg.hypernate.registry.query;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Immutable representation of a built query. Adapters may consume this object
 * to produce provider-specific selectors.
 */
public final class Query {
    private final List<Condition> conditions;
    private final List<Sort> sorts;
    private final Integer limit;

    public Query(List<Condition> conditions, List<Sort> sorts, Integer limit) {
        this.conditions = Collections.unmodifiableList(Objects.requireNonNull(conditions));
        this.sorts = sorts == null ? Collections.emptyList() : Collections.unmodifiableList(sorts);
        this.limit = limit;
    }

    public List<Condition> getConditions() { return conditions; }

    public List<Sort> getSorts() { return sorts; }

    public Integer getLimit() { return limit; }
}
