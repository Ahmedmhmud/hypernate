package hu.bme.mit.ftsrg.hypernate.registry.query;

import java.util.Arrays;
import java.util.Objects;

/**
 * Immutable representation of a single field condition.
 *
 * <p>A condition specifies a field name, an operator (e.g., EQ, GT, IN), and one or
 * more values. Conditions are collected by {@link QueryBuilder} and exposed in the
 * {@link Query} object for adapters to translate to provider-specific formats.
 *
 * <p>Example: A condition created by {@code .where("size").greaterThan(10)} produces:
 * <ul>
 *   <li>field: "size"</li>
 *   <li>operator: Operator.GT</li>
 *   <li>values: [10]</li>
 * </ul>
 *
 * @see Query
 * @see Operator
 */
public final class Condition {
    private final String field;
    private final Operator operator;
    private final Object[] values;

    /**
     * Construct a condition with a field, operator, and values.
     *
     * @param field the field name (must not be null)
     * @param operator the operator (must not be null)
     * @param values zero or more values (null is converted to empty array)
     */
    public Condition(String field, Operator operator, Object... values) {
        this.field = Objects.requireNonNull(field);
        this.operator = Objects.requireNonNull(operator);
        this.values = values == null ? new Object[0] : values.clone();
    }

    /**
     * Get the field name.
     *
     * @return the field name
     */
    public String getField() { return field; }

    /**
     * Get the operator.
     *
     * @return the operator
     */
    public Operator getOperator() { return operator; }

    /**
     * Get the values (defensive copy).
     *
     * @return a copy of the values array
     */
    public Object[] getValues() { return values.clone(); }

    @Override
    public String toString() {
        return "Condition{" +
            "field='" + field + '\'' +
            ", operator=" + operator +
            ", values=" + Arrays.toString(values) +
            '}';
    }
}

