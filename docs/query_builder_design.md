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
  terminal condition methods `is`, `greaterThan`, `in`, `exists`, `matchesRegex`.
  Terminal methods return back to `QueryBuilder<T>` for chaining.
- `Query` — immutable representation containing a list of `Condition` and
  `Sort` entries plus optional `limit`.
- `Condition`, `Operator`, `Sort`, `SortOrder` — lightweight DTOs used by
  adapters.
- `SimpleQueryBuilder<T>` — concrete implementation demonstrating the fluent API.
  Accepts a `QueryExecutor<T>` callback to delegate actual data retrieval to the
  Registry or other adapter.

Adapter contract
- Registry implementations should provide an adapter that accepts `Query` and
  produces provider-specific queries (CouchDB rich query JSON, Mango selector,
  SQL, etc.). This separates developer surface from transport/implementation.

Extensibility
- New operators can be added to `Operator` and mapped in adapters.
- Support for nested/compound boolean logic (groups) can be added by
  introducing a `ConditionGroup` type if needed.
- Additional terminal conditions (e.g., `between`, `contains`, `startsWith`)
  can be added to `FieldConditionBuilder` and `Operator`.

## Usage

### Developer Experience

```java
// Simple query
List<Asset> results = registry.query(Asset.class)
    .where("color").is("blue")
    .execute();

// Complex query with multiple conditions, sorting, and limit
List<Asset> results = registry.query(Asset.class)
    .where("color").is("blue")
    .and("size").greaterThan(10)
    .and("owner").in("Alice", "Bob")
    .sortBy("value", SortOrder.DESC)
    .limit(50)
    .execute();

// Access underlying immutable Query for adapters
Query q = registry.query(Asset.class)
    .where("status").is("active")
    .toQuery();
// Use q.getConditions(), q.getSorts(), q.getLimit() to build provider-specific queries
```

### Testing

The `SimpleQueryBuilder` can be tested independently without a Registry. See
`SimpleQueryBuilderTest` for examples.

## Implementation Status

- **Done**: Core interfaces and DTOs (QueryBuilder, FieldConditionBuilder, Query, Condition, Operator, Sort, SortOrder).
- **Done**: SimpleQueryBuilder concrete implementation.
- **Done**: Registry#query(Class<T>) entry point.
- **TODO**: Adapter that translates Query → CouchDB rich query JSON.
- **TODO**: Adapter that translates Query → SQL WHERE clause.
- **TODO**: Integration test with actual CouchDB backend.

## Next Steps
- Add an adapter that translates `Query` -> CouchDB selector JSON for future
  integration with CouchDB-backed Registry implementations.
- Implement per-operator translation logic for different backends (CouchDB, SQL, etc.).
- Add comprehensive integration tests.
