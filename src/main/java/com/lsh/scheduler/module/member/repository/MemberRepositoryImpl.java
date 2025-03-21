package com.lsh.scheduler.module.member.repository;

import com.lsh.scheduler.module.member.domain.model.Member;
import com.lsh.scheduler.module.member.dto.MemberCreateRequestDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class MemberRepositoryImpl implements MemberRepository {
    private final JdbcTemplate jdbcTemplate;

    public MemberRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public Optional<Member> save(MemberCreateRequestDto dto) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate);
        simpleJdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");

        LocalDateTime now = LocalDateTime.now();
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", dto.getName());
        parameters.put("email", dto.getEmail());
        parameters.put("created_at", now);
        parameters.put("modified_at", now);

        Number key = simpleJdbcInsert.executeAndReturnKey(parameters);

        return findById(key.longValue());
    }

    @Override
    public Optional<Member> findById(Long id) {
        String sql = "select * from scheduler.member where id = ?";

        return jdbcTemplate.query(sql,
                (rs, rowNum) -> Member.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .email(rs.getString("email"))
                        .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                        .modifiedAt(rs.getTimestamp("modified_at").toLocalDateTime())
                        .build(), id).stream().findAny();
    }

}
