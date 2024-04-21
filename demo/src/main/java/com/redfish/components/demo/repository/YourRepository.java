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
        jdbcTemplate.update("INSERT INTO your_table (code, name) VALUES (?, ?)",
                ps -> {
                    ps.setString(1, "123");
                    ps.setString(2, "456");
                });

    }
}
