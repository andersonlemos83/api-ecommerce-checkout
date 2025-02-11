package br.com.alc.ecommerce.checkout.infrastructure.helper.manager.impl;

import br.com.alc.ecommerce.checkout.infrastructure.helper.manager.PostgresManager;
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
public class PostgresManagerImpl implements PostgresManager {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void removeForeignKeys() {
        List<Map<String, Object>> constraints = jdbcTemplate.queryForList("SELECT * FROM INFORMATION_SCHEMA.TABLE_CONSTRAINTS WHERE CONSTRAINT_SCHEMA IN ('public')");
        constraints.stream()
                .parallel()
                .filter(constraint -> "FOREIGN KEY".equals(constraint.get("CONSTRAINT_TYPE")))
                .forEach(constraint -> {
                    String tableSchema = (String) constraint.get("TABLE_SCHEMA");
                    String tableName = (String) constraint.get("TABLE_NAME");
                    String constraintName = (String) constraint.get("CONSTRAINT_NAME");
                    String sql = format("ALTER TABLE {0}.{1} DISABLE TRIGGER {2}", tableSchema, tableName, constraintName);
                    log.info("--> {}", sql);
                    jdbcTemplate.execute(sql);
                });
    }

    @Override
    public void cleanDatabase() {
        List<Map<String, Object>> tables = jdbcTemplate.queryForList("SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA IN ('public')");
        tables.stream()
                .parallel()
                .forEach(table -> {
                    String tableSchema = (String) table.get("TABLE_SCHEMA");
                    String tableName = (String) table.get("TABLE_NAME");
                    String sql = format("TRUNCATE TABLE {0}.{1}", tableSchema, tableName);
                    log.info("--> {}", sql);
                    jdbcTemplate.execute(sql);
                });
    }

    @Override
    public void resetSequences() {
        List<Map<String, Object>> sequences = jdbcTemplate.queryForList("SELECT * FROM INFORMATION_SCHEMA.SEQUENCES WHERE SEQUENCE_SCHEMA IN ('public')");
        sequences.forEach(sequence -> {
            String sequenceSchema = (String) sequence.get("SEQUENCE_SCHEMA");
            String sequenceName = (String) sequence.get("SEQUENCE_NAME");

            String dropSql = format("DROP SEQUENCE {0}.{1}", sequenceSchema, sequenceName);
            log.info("--> {}", dropSql);
            jdbcTemplate.execute(dropSql);

            String createSql = format("CREATE SEQUENCE {0}.{1} MINVALUE 1 START WITH 1 INCREMENT BY 1 NO CYCLE", sequenceSchema, sequenceName);
            log.info("--> {}", createSql);
            jdbcTemplate.execute(createSql);
        });
    }
}