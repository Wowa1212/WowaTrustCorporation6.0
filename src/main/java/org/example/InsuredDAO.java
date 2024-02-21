package org.example;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
@Repository
public class InsuredDAO {
    private final JdbcTemplate jdbcTemplate;

    public InsuredDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertInsured(Insured insured) {
        String sql = "INSERT INTO insured (name, surname, address, bankAccountNumber) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, insured.getName(), insured.getSurname(), insured.getAddress(), insured.getBankAccountNumber());
    }
}