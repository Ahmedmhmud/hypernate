package hu.bme.mit.ftsrg.hypernate.registry.query;

/**
 * Atomic operators supported by the query abstraction.
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
