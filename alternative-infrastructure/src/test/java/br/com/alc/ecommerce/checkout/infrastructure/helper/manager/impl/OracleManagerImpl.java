package br.com.alc.ecommerce.checkout.infrastructure.helper.manager.impl;

import br.com.alc.ecommerce.checkout.infrastructure.helper.manager.OracleManager;
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
public class OracleManagerImpl implements OracleManager {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void removeForeignKeys() {
//        List<Map<String, Object>> constraints = jdbcTemplate.queryForList("SELECT * FROM ALL_CONSTRAINTS WHERE OWNER IN ('ECOMMERCE_CHECKOUT_OWNER')");
//        constraints.stream()
//                .parallel()
//                .filter(constraint -> "R".equals(constraint.get("CONSTRAINT_TYPE")))
//                .forEach(constraint -> {
//                    String tableSchema = (String) constraint.get("OWNER");
//                    String tableName = (String) constraint.get("TABLE_NAME");
//                    String constraintName = (String) constraint.get("CONSTRAINT_NAME");
//                    String sql = format("ALTER TABLE {0}.{1} DISABLE CONSTRAINT {2}", tableSchema, tableName, constraintName);
//                    log.info("--> {}", sql);
//                    jdbcTemplate.execute(sql);
//                });
    }

    @Override
    public void cleanDatabase() {
//        List<Map<String, Object>> tables = jdbcTemplate.queryForList("SELECT * FROM ALL_TABLES WHERE OWNER IN ('ECOMMERCE_CHECKOUT_OWNER')");
//        tables.stream()
//                .parallel()
//                .forEach(table -> {
//                    String tableSchema = (String) table.get("OWNER");
//                    String tableName = (String) table.get("TABLE_NAME");
//                    String sql = format("TRUNCATE TABLE {0}.{1}", tableSchema, tableName);
//                    log.info("--> {}", sql);
//                    jdbcTemplate.execute(sql);
//                });
    }

    @Override
    public void resetSequences() {
//        List<Map<String, Object>> sequences = jdbcTemplate.queryForList("SELECT * FROM ALL_SEQUENCES WHERE SEQUENCE_OWNER IN ('ECOMMERCE_CHECKOUT_OWNER')");
//        sequences.forEach(sequence -> {
//            String sequenceSchema = (String) sequence.get("SEQUENCE_OWNER");
//            String sequenceName = (String) sequence.get("SEQUENCE_NAME");
//
//            String dropSql = format("DROP SEQUENCE {0}.{1}", sequenceSchema, sequenceName);
//            log.info("--> {}", dropSql);
//            jdbcTemplate.execute(dropSql);
//
//            String createSql = format("CREATE SEQUENCE {0}.{1} START WITH 1 INCREMENT BY 1", sequenceSchema, sequenceName);
//            log.info("--> {}", createSql);
//            jdbcTemplate.execute(createSql);
//        });
    }
}