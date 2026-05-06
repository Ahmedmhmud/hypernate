package hu.bme.mit.ftsrg.hypernate.registry.query;

import java.util.Arrays;
import java.util.Objects;

/**
 * Immutable representation of a single field condition.
 */
public final class Condition {
    private final String field;
    private final Operator operator;
    private final Object[] values;

    public Condition(String field, Operator operator, Object... values) {
        this.field = Objects.requireNonNull(field);
        this.operator = Objects.requireNonNull(operator);
        this.values = values == null ? new Object[0] : values.clone();
    }

    public String getField() { return field; }

    public Operator getOperator() { return operator; }

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
