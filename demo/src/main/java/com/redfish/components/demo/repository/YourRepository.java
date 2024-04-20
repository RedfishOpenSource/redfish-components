package com.redfish.components.demo.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class YourRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public YourRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void yourDatabaseOperation() {
        // 使用jdbcTemplate进行数据库操作
        jdbcTemplate.update("INSERT INTO your_table (column1, column2) VALUES (?, ?)",
                ps -> {
                    ps.setString(1, "value1");
                    ps.setString(2, "value2");
                });

    }
}
