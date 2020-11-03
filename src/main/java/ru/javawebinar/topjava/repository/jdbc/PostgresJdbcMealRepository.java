package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ru.javawebinar.topjava.Profiles;

import java.time.LocalDateTime;

@Profile(Profiles.POSTGRES_DB)
public class PostgresJdbcMealRepository extends JdbcMealRepository<LocalDateTime> {
    public PostgresJdbcMealRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    public LocalDateTime castDateTime(LocalDateTime localDateTime) {
        return localDateTime;
    }
}
