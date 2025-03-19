package com.lsh.scheduler.module.member.repository;

import com.lsh.scheduler.module.member.domain.model.Member;
import com.lsh.scheduler.module.member.dto.MemberRequestDto;
import com.lsh.scheduler.module.member.dto.MemberResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpServerErrorException;

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
    public MemberResponseDto save(MemberRequestDto dto) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate);
        simpleJdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");

        LocalDateTime now = LocalDateTime.now();
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", dto.getName());
        parameters.put("email", dto.getEmail());
        parameters.put("createdAt", now);
        parameters.put("modifiedAt", now);

        Number key = simpleJdbcInsert.executeAndReturnKey(parameters);

        Member member = findById(key.longValue())
                .orElseThrow(() -> new HttpServerErrorException(HttpStatus.NOT_FOUND));

        return Member.toDto(member);
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
