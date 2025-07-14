package br.com.alc.ecommerce.checkout.infrastructure.helper.manager.impl;

import br.com.alc.ecommerce.checkout.infrastructure.helper.manager.H2Manager;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static java.text.MessageFormat.format;

@Log4j2
@Component
@AllArgsConstructor
public class H2ManagerImpl implements H2Manager {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void removeForeignKeys() {
        List<Map<String, Object>> constraints = jdbcTemplate.queryForList("SELECT * FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS");
        constraints.stream()
                .parallel()
                .filter(constraint -> "ECOMMERCE_CHECKOUT_OWNER".equals(constraint.get("TABLE_SCHEMA")) && "FOREIGN KEY".equals(constraint.get("CONSTRAINT_TYPE")))
                .forEach(constraint -> {
                    String tableSchema = (String) constraint.get("TABLE_SCHEMA");
                    String tableName = (String) constraint.get("TABLE_NAME");
                    String constraintName = (String) constraint.get("CONSTRAINT_NAME");
                    String truncateSql = format("ALTER TABLE {0}.{1} DROP CONSTRAINT {2} RESTRICT", tableSchema, tableName, constraintName);
                    log.info("--> {}", truncateSql);
                    jdbcTemplate.execute(truncateSql);
                });
    }

    @Override
    public void cleanDatabase() {
        List<Map<String, Object>> tables = jdbcTemplate.queryForList("SELECT * FROM INFORMATION_SCHEMA.TABLES");
        tables.stream()
                .parallel()
                .filter(table -> "ECOMMERCE_CHECKOUT_OWNER".equals(table.get("TABLE_SCHEMA")))
                .forEach(table -> {
                    String tableSchema = (String) table.get("TABLE_SCHEMA");
                    String tableName = (String) table.get("TABLE_NAME");
                    String truncateSql = format("TRUNCATE TABLE {0}.{1}", tableSchema, tableName);
                    log.info("--> {}", truncateSql);
                    jdbcTemplate.execute(truncateSql);
                });
    }

    @Override
    public void resetSequences() {
        List<Map<String, Object>> sequences = jdbcTemplate.queryForList("SELECT * FROM INFORMATION_SCHEMA.SEQUENCES");
        sequences.forEach(sequence -> {
            String sequenceSchema = (String) sequence.get("SEQUENCE_SCHEMA");
            String sequenceName = (String) sequence.get("SEQUENCE_NAME");
            jdbcTemplate.execute(format("ALTER SEQUENCE {0}.{1} RESTART WITH 1", sequenceSchema, sequenceName));
        });
    }
}