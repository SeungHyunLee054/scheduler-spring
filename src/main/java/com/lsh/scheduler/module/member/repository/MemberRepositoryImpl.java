package com.lsh.scheduler.module.member.repository;

import com.lsh.scheduler.module.member.domain.model.Member;
import com.lsh.scheduler.module.member.dto.MemberCreateRequestDto;
import com.lsh.scheduler.module.member.exception.MemberException;
import com.lsh.scheduler.module.member.exception.MemberExceptionCode;
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
    public Member save(MemberCreateRequestDto dto) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate);
        simpleJdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");

        LocalDateTime now = LocalDateTime.now();
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", dto.getName());
        parameters.put("email", dto.getEmail());
        parameters.put("createdAt", now);
        parameters.put("modifiedAt", now);

        Number key = simpleJdbcInsert.executeAndReturnKey(parameters);

        return findById(key.longValue())
                .orElseThrow(() -> new MemberException(MemberExceptionCode.NOT_FOUND));
    }

    @Override
    public Optional<Member> findById(Long id) {
        String sql = "select * from scheduler.member where id = ?";

        return jdbcTemplate.query(sql,
                (rs, rowNum) -> Member.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .email(rs.getString("email"))
                        .createdAt(rs.getTimestamp("createdAt").toLocalDateTime())
                        .modifiedAt(rs.getTimestamp("modifiedAt").toLocalDateTime())
                        .build(), id).stream().findAny();
    }

}
