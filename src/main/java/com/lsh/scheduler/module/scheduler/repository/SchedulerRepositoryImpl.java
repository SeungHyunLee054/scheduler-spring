package com.lsh.scheduler.module.scheduler.repository;

import com.lsh.scheduler.common.utils.PasswordUtils;
import com.lsh.scheduler.module.member.domain.model.Member;
import com.lsh.scheduler.module.member.exception.MemberException;
import com.lsh.scheduler.module.member.exception.MemberExceptionCode;
import com.lsh.scheduler.module.member.repository.MemberRepository;
import com.lsh.scheduler.module.scheduler.domain.model.Scheduler;
import com.lsh.scheduler.module.scheduler.dto.SchedulerCreateRequestDto;
import com.lsh.scheduler.module.scheduler.dto.SchedulerUpdateRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Repository
public class SchedulerRepositoryImpl implements SchedulerRepository {
    private final JdbcTemplate jdbcTemplate;
    private final MemberRepository memberRepository;

    public SchedulerRepositoryImpl(DataSource dataSource, MemberRepository memberRepository) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.memberRepository = memberRepository;
    }


    @Override
    public Optional<Scheduler> saveScheduler(SchedulerCreateRequestDto schedulerCreateRequestDto) {
        Member member = memberRepository.findById(schedulerCreateRequestDto.getMemberId())
                .orElseThrow(() -> new MemberException(MemberExceptionCode.NOT_FOUND));

        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate);
        simpleJdbcInsert.withTableName("scheduler").usingGeneratedKeyColumns("id");

        LocalDateTime now = LocalDateTime.now();
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("member_id", member.getId());
        parameters.put("task", schedulerCreateRequestDto.getTask());
        // 비밀번호를 passwordEncoder로 암호화하여 저장
        parameters.put("password", PasswordUtils.encode(schedulerCreateRequestDto.getPassword()));
        parameters.put("createdAt", now);
        parameters.put("modifiedAt", now);

        Number key = simpleJdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return findById(key.longValue());
    }

    @Override
    public Optional<Scheduler> findById(long id) {
        String sql = "select * from scheduler.scheduler where id = ?";

        List<Scheduler> schedulerList = jdbcTemplate.query(sql, (rs, rowNum) -> getBuild(rs), id);

        return schedulerList.stream().findAny();
    }

    @Override
    public Page<Scheduler> findAll(String name, LocalDate modifiedAt, Pageable pageable) {
        StringBuilder sql = new StringBuilder("select * from scheduler.scheduler s");
        List<Object> parameters = new ArrayList<>();

        if (name == null && modifiedAt != null) {
            sql.append(" where date(s.modifiedAt) = ?");
            parameters.add(modifiedAt);
        } else if (name != null && modifiedAt == null) {
            sql.append(" join scheduler.member m on m.id = s.member_id");
            sql.append("  where m.name = ?");
            parameters.add(name);
        } else if (name != null) {
            sql.append(" s");
            sql.append(" join scheduler.member m on m.id = s.member_id");
            sql.append("  where m.name = ?");
            sql.append("  and date(s.modifiedAt) = ?");
            parameters.add(name);
            parameters.add(modifiedAt);
        }

        sql.append("  order by s.modifiedAt desc");
        sql.append("  limit ? offset ?");
        parameters.add(pageable.getPageSize());
        parameters.add(pageable.getOffset());

        List<Scheduler> result = jdbcTemplate.query(sql.toString(), (rs, rowNum) -> getBuild(rs),
                parameters.toArray());

        return new PageImpl<>(result, pageable, getTotalCnt());
    }

    @Override
    public Optional<Scheduler> updateScheduler(SchedulerUpdateRequestDto dto) {
        String sql = "update scheduler.scheduler s " +
                "join scheduler.member m on m.id = s.member_id " +
                "set s.task=?,m.name=? where s.id=?";
        int row = jdbcTemplate.update(sql, dto.getTask(), dto.getMemberName(), dto.getSchedulerId());
        if (row > 0) {
            return findById(dto.getSchedulerId());
        }

        return Optional.empty();
    }

    @Override
    public Optional<Scheduler> deleteSchedulerById(long schedulerId) {
        String sql = "delete from scheduler.scheduler where id=?";
        Optional<Scheduler> scheduler = findById(schedulerId);

        int row = jdbcTemplate.update(sql, schedulerId);
        if (row > 0) {
            return scheduler;
        }

        return Optional.empty();
    }

    /**
     * row mapper
     *
     * @param rs result set
     * @return 일정
     */
    private Scheduler getBuild(ResultSet rs) throws SQLException {
        return Scheduler.builder()
                .id(rs.getLong("id"))
                .task(rs.getString("task"))
                .member(memberRepository.findById(rs.getLong("member_id"))
                        .orElseThrow(() -> new MemberException(MemberExceptionCode.NOT_FOUND)))
                .password(rs.getString("password"))
                .createdAt(rs.getTimestamp("createdAt").toLocalDateTime())
                .modifiedAt(rs.getTimestamp("modifiedAt").toLocalDateTime())
                .build();
    }

    /**
     * 전체 일정 수 조회
     *
     * @return 전체 일정 수
     */
    private int getTotalCnt() {
        String countSql = "select count(*) from scheduler.scheduler";
        Integer totalCnt = jdbcTemplate.queryForObject(countSql, Integer.class);

        return totalCnt == null ? 0 : totalCnt;
    }

}
