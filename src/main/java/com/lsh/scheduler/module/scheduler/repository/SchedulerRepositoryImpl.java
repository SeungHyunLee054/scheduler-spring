package com.lsh.scheduler.module.scheduler.repository;

import com.lsh.scheduler.module.member.domain.model.Member;
import com.lsh.scheduler.module.member.exception.MemberException;
import com.lsh.scheduler.module.member.exception.MemberExceptionCode;
import com.lsh.scheduler.module.member.repository.MemberRepository;
import com.lsh.scheduler.module.scheduler.domain.model.Scheduler;
import com.lsh.scheduler.module.scheduler.dto.SchedulerCreateRequestDto;
import com.lsh.scheduler.module.scheduler.dto.SchedulerDeleteRequestDto;
import com.lsh.scheduler.module.scheduler.dto.SchedulerUpdateRequestDto;
import com.lsh.scheduler.module.scheduler.exception.SchedulerException;
import com.lsh.scheduler.module.scheduler.exception.SchedulerExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class SchedulerRepositoryImpl implements SchedulerRepository {
    private final JdbcTemplate jdbcTemplate;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public SchedulerRepositoryImpl(DataSource dataSource, MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public Scheduler saveScheduler(SchedulerCreateRequestDto schedulerCreateRequestDto) {
        Member member = memberRepository.findById(schedulerCreateRequestDto.getMemberId())
                .orElseThrow(() -> new MemberException(MemberExceptionCode.NOT_FOUND));

        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate);
        simpleJdbcInsert.withTableName("scheduler").usingGeneratedKeyColumns("id");

        LocalDateTime now = LocalDateTime.now();
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("member_id", member.getId());
        parameters.put("task", schedulerCreateRequestDto.getTask());
        parameters.put("password", passwordEncoder.encode(schedulerCreateRequestDto.getPassword()));
        parameters.put("createdAt", now);
        parameters.put("modifiedAt", now);

        Number key = simpleJdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return findById(key.longValue())
                .orElseThrow(() -> new SchedulerException(SchedulerExceptionCode.NOT_FOUND));
    }

    @Override
    public Optional<Scheduler> findById(long id) {
        String sql = "select * from scheduler.scheduler where id = ?";

        List<Scheduler> schedulerList = jdbcTemplate.query(sql, (rs, rowNum) -> getBuild(rs), id);

        return schedulerList.stream().findAny();
    }

    @Override
    public Page<Scheduler> findAll(Pageable pageable) {
        String selectSql = "select * from scheduler.scheduler order by modifiedAt desc limit ? offset ?";

        List<Scheduler> result = jdbcTemplate.query(selectSql, (rs, rowNum) -> getBuild(rs),
                pageable.getPageSize(), pageable.getOffset());

        return new PageImpl<>(result, pageable, getTotalCnt());
    }

    @Override
    public Page<Scheduler> findAllByName(String name, Pageable pageable) {
        String sql = "select * " +
                "from scheduler.scheduler s " +
                "join scheduler.member m on m.id = s.member_id " +
                "where m.name = ? " +
                "order by s.modifiedAt desc " +
                "limit ? offset ?";

        List<Scheduler> result = jdbcTemplate.query(sql, (rs, rowNum) -> getBuild(rs),
                name, pageable.getPageSize(), pageable.getOffset());

        return new PageImpl<>(result, pageable, getTotalCnt());
    }

    @Override
    public Page<Scheduler> findAllByModifiedAt(LocalDate modifiedAt, Pageable pageable) {
        String sql = "select * " +
                "from scheduler.scheduler " +
                "where date(scheduler.modifiedAt) = ? " +
                "order by modifiedAt desc " +
                "limit ? offset ?";

        List<Scheduler> result = jdbcTemplate.query(sql, (rs, rowNum) -> getBuild(rs),
                modifiedAt, pageable.getPageSize(), pageable.getOffset());

        return new PageImpl<>(result, pageable, getTotalCnt());
    }

    @Override
    public Page<Scheduler> findAllByNameAndModifiedAt(String name, LocalDate modifiedAt, Pageable pageable) {
        String sql = "select * " +
                "from scheduler.scheduler s " +
                "join scheduler.member m on m.id = s.member_id " +
                "where m.name = ? and date(s.modifiedAt) = ? " +
                "order by s.modifiedAt desc " +
                "limit ? offset ?";

        List<Scheduler> result = jdbcTemplate.query(sql, (rs, rowNum) -> getBuild(rs)
                , name, modifiedAt, pageable.getPageSize(), pageable.getOffset());

        return new PageImpl<>(result, pageable, getTotalCnt());
    }


    @Override
    public Optional<Scheduler> updateScheduler(SchedulerUpdateRequestDto dto) {
        validatePassword(dto.getSchedulerId(), dto.getPassword());

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
    public Optional<Scheduler> deleteSchedulerById(SchedulerDeleteRequestDto dto) {
        validatePassword(dto.getSchedulerId(), dto.getPassword());

        String sql = "delete from scheduler.scheduler where id=?";
        Optional<Scheduler> scheduler = findById(dto.getSchedulerId());

        int row = jdbcTemplate.update(sql, dto.getSchedulerId());
        if (row > 0) {
            return scheduler;
        }

        return Optional.empty();
    }

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

    private int getTotalCnt() {
        String countSql = "select count(*) from scheduler.scheduler";
        Integer totalCnt = jdbcTemplate.queryForObject(countSql, Integer.class);

        return totalCnt == null ? 0 : totalCnt;
    }

    private void validatePassword(long id, String password) {
        Scheduler scheduler = findById(id)
                .orElseThrow(() -> new SchedulerException(SchedulerExceptionCode.NOT_FOUND));
        if (!passwordEncoder.matches(password, scheduler.getPassword())){
            throw new SchedulerException(SchedulerExceptionCode.WRONG_PASSWORD);
        }
    }

}
