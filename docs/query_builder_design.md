# Query Builder Design

This document describes the fluent rich-query abstraction for the `Registry`.

Goals
- Provide a fluent, type-parameterized API for building rich queries (CouchDB
  selectors, SQL WHERE-like predicates, etc.).
- Keep the design adapter-friendly: an immutable `Query` object is produced
  which adapters can translate to provider-specific formats.

Core types
- `QueryBuilder<T>` — public fluent surface used by callers. Methods: `where`,
  `and`, `or`, `sortBy`, `limit`, `execute`, `toQuery`.
- `FieldConditionBuilder<T>` — returned by `where`/`and`/`or` and provides the
  terminal condition methods `is`, `greaterThan`, `in`, etc. Terminal methods
  return back to `QueryBuilder<T>` for chaining.
- `Query` — immutable representation containing a list of `Condition` and
  `Sort` entries plus optional `limit`.
- `Condition`, `Operator`, `Sort`, `SortOrder` — lightweight DTOs used by
  adapters.

Adapter contract
- Registry implementations should provide an adapter that accepts `Query` and
  produces provider-specific queries (CouchDB rich query JSON, Mango selector,
  SQL, etc.). This separates developer surface from transport/implementation.

Extensibility
- New operators can be added to `Operator` and mapped in adapters.
- Support for nested/compound boolean logic (groups) can be added by
  introducing a `ConditionGroup` type if needed.

Example usage
```
List<Asset> results = registry.query(Asset.class)
    .where("color").is("blue")
    .and("size").greaterThan(10)
    .and("owner").in("Alice", "Bob")
    .sortBy("value", SortOrder.DESC)
    .limit(50)
    .execute();
```

Next steps
- Add `Registry#query(Class<T>)` entry point returning `QueryBuilder<T>`.
- Implement a simple in-memory `QueryBuilder` for local tests.
- Add an adapter that translates `Query` -> CouchDB selector JSON for future
  integration.
