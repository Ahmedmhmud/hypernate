package hu.bme.mit.ftsrg.hypernate.registry.query.impl;

import hu.bme.mit.ftsrg.hypernate.registry.query.*;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for SimpleQueryBuilder demonstrating the fluent query API.
 */
public class SimpleQueryBuilderTest {

    private SimpleQueryBuilder<TestEntity> builder;

    @BeforeEach
    void setUp() {
        builder = new SimpleQueryBuilder<>(TestEntity.class);
    }

    @Test
    void testWhereIsCondition() {
        SimpleQueryBuilder<TestEntity> result = (SimpleQueryBuilder<TestEntity>) 
            builder.where("color").is("blue");
        
        Query query = result.toQuery();
        assertNotNull(query);
        assertEquals(1, query.getConditions().size());
        
        Condition cond = query.getConditions().get(0);
        assertEquals("color", cond.getField());
        assertEquals(Operator.EQ, cond.getOperator());
        assertEquals("blue", cond.getValues()[0]);
    }

    @Test
    void testMultipleConditions() {
        SimpleQueryBuilder<TestEntity> result = (SimpleQueryBuilder<TestEntity>)
            builder.where("color").is("blue")
                   .and("size").greaterThan(10);
        
        Query query = result.toQuery();
        assertEquals(2, query.getConditions().size());
        
        assertEquals("color", query.getConditions().get(0).getField());
        assertEquals(Operator.EQ, query.getConditions().get(0).getOperator());
        
        assertEquals("size", query.getConditions().get(1).getField());
        assertEquals(Operator.GT, query.getConditions().get(1).getOperator());
    }

    @Test
    void testInCondition() {
        SimpleQueryBuilder<TestEntity> result = (SimpleQueryBuilder<TestEntity>)
            builder.where("owner").in("Alice", "Bob", "Charlie");
        
        Query query = result.toQuery();
        assertEquals(1, query.getConditions().size());
        
        Condition cond = query.getConditions().get(0);
        assertEquals("owner", cond.getField());
        assertEquals(Operator.IN, cond.getOperator());
        assertEquals(3, cond.getValues().length);
    }

    @Test
    void testSortBy() {
        SimpleQueryBuilder<TestEntity> result = (SimpleQueryBuilder<TestEntity>)
            builder.sortBy("value", SortOrder.DESC);
        
        Query query = result.toQuery();
        assertEquals(1, query.getSorts().size());
        assertEquals("value", query.getSorts().get(0).getField());
        assertEquals(SortOrder.DESC, query.getSorts().get(0).getOrder());
    }

    @Test
    void testLimit() {
        SimpleQueryBuilder<TestEntity> result = (SimpleQueryBuilder<TestEntity>)
            builder.limit(50);
        
        Query query = result.toQuery();
        assertEquals(50, query.getLimit());
    }

    @Test
    void testComplexQuery() {
        SimpleQueryBuilder<TestEntity> result = (SimpleQueryBuilder<TestEntity>)
            builder.where("color").is("blue")
                   .and("size").greaterThan(10)
                   .and("owner").in("Alice", "Bob")
                   .sortBy("value", SortOrder.DESC)
                   .limit(50);
        
        Query query = result.toQuery();
        
        // Check conditions
        assertEquals(3, query.getConditions().size());
        
        // Check sorts
        assertEquals(1, query.getSorts().size());
        assertEquals("value", query.getSorts().get(0).getField());
        
        // Check limit
        assertEquals(50, query.getLimit());
    }

    @Test
    void testLessThanCondition() {
        SimpleQueryBuilder<TestEntity> result = (SimpleQueryBuilder<TestEntity>)
            builder.where("age").lessThan(30);
        
        Query query = result.toQuery();
        assertEquals(1, query.getConditions().size());
        assertEquals(Operator.LT, query.getConditions().get(0).getOperator());
    }

    @Test
    void testExistsCondition() {
        SimpleQueryBuilder<TestEntity> result = (SimpleQueryBuilder<TestEntity>)
            builder.where("profile").exists();
        
        Query query = result.toQuery();
        assertEquals(1, query.getConditions().size());
        assertEquals(Operator.EXISTS, query.getConditions().get(0).getOperator());
    }

    @Test
    void testRegexCondition() {
        SimpleQueryBuilder<TestEntity> result = (SimpleQueryBuilder<TestEntity>)
            builder.where("email").matchesRegex(".*@example\\.com$");
        
        Query query = result.toQuery();
        assertEquals(1, query.getConditions().size());
        assertEquals(Operator.REGEX, query.getConditions().get(0).getOperator());
    }

    @Test
    void testToQuery() {
        builder.where("field1").is("value1");
        Query query = builder.toQuery();
        
        assertNotNull(query);
        assertNotNull(query.getConditions());
        assertNotNull(query.getSorts());
        assertNull(query.getLimit());
    }

    @Test
    void testExecuteWithoutExecutor() {
        builder.where("field1").is("value1");
        
        assertThrows(UnsupportedOperationException.class, () -> builder.execute());
    }

    @Test
    void testExecuteWithExecutor() {
        builder.where("color").is("blue");
        
        // Mock executor that returns a fixed list
        builder.setExecutor(query -> List.of(
            new TestEntity("1", "blue"),
            new TestEntity("2", "blue")
        ));
        
        List<TestEntity> results = builder.execute();
        assertEquals(2, results.size());
    }

    @Test
    void testIllegalStateWhereWithoutField() {
        builder.is("value");
        
        assertThrows(IllegalStateException.class, () -> builder.is("value"));
    }

    /**
     * Simple test entity for query builder tests.
     */
    public static class TestEntity {
        private String id;
        private String color;

        public TestEntity(String id, String color) {
            this.id = id;
            this.color = color;
        }

        public String getId() { return id; }
        public String getColor() { return color; }
    }
}
