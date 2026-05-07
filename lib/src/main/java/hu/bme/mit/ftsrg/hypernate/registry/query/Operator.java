package hu.bme.mit.ftsrg.hypernate.registry.query;

/**
 * Enumeration of operators supported by the query abstraction.
 *
 * <p>Each operator corresponds to a terminal condition method in {@link FieldConditionBuilder}:
 * <ul>
 *   <li>EQ — equality (is)</li>
 *   <li>GT — greater than</li>
 *   <li>LT — less than</li>
 *   <li>GTE — greater than or equal</li>
 *   <li>LTE — less than or equal</li>
 *   <li>IN — set membership</li>
 *   <li>EXISTS — field exists / is not null</li>
 *   <li>REGEX — regex match</li>
 * </ul>
 *
 * <p>Adapters can extend this list if additional operators are needed for their
 * provider-specific query language.
 */
public enum Operator {
    EQ,
    GT,
    LT,
    GTE,
    LTE,
    IN,
    EXISTS,
    REGEX
}

