package com.PostMvc.PostMvc;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;


@SpringBootTest
public class DatabaseConnectionTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @TestConfiguration
   static class TestConfig {
       private final DataSource dataSource;

       @Autowired
       public TestConfig(DataSource dataSource) {
           this.dataSource = dataSource;
       }
   }

    @Test
    public void testDatabaseConnection() {
        String sql = "SELECT 1";
        Integer result = jdbcTemplate.queryForObject(sql,Integer.class);

        Assertions.assertThat(result).isEqualTo(1);
    }
}

