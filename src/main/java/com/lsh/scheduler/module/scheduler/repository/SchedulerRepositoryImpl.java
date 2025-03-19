package com.lsh.scheduler.module.scheduler.repository;

import com.lsh.scheduler.module.member.domain.model.Member;
import com.lsh.scheduler.module.member.repository.MemberRepository;
import com.lsh.scheduler.module.scheduler.domain.model.Scheduler;
import com.lsh.scheduler.module.scheduler.dto.DeleteSchedulerRequestDto;
import com.lsh.scheduler.module.scheduler.dto.SchedulerRequestDto;
import com.lsh.scheduler.module.scheduler.dto.SchedulerResponseDto;
import com.lsh.scheduler.module.scheduler.dto.UpdateSchedulerRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpServerErrorException;

import javax.sql.DataSource;
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

    public SchedulerRepositoryImpl(DataSource dataSource, MemberRepository memberRepository) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.memberRepository = memberRepository;
    }


    @Override
    public SchedulerResponseDto saveScheduler(SchedulerRequestDto schedulerRequestDto) {
        Member member = memberRepository.findById(schedulerRequestDto.getMemberId())
                .orElseThrow(() -> new HttpServerErrorException(HttpStatus.NOT_FOUND));

        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate);
        simpleJdbcInsert.withTableName("scheduler").usingGeneratedKeyColumns("id");

        LocalDateTime now = LocalDateTime.now();
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("member_id", member.getId());
        parameters.put("task", schedulerRequestDto.getTask());
        parameters.put("password", schedulerRequestDto.getPassword());
        parameters.put("createdAt", now);
        parameters.put("modifiedAt", now);

        Number key = simpleJdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        Scheduler scheduler = findById(key.longValue())
                .orElseThrow(() -> new HttpServerErrorException(HttpStatus.NOT_FOUND));

        return Scheduler.toDto(scheduler);
    }

    @Override
    public Optional<Scheduler> findById(long id) {
        String sql = "select * from scheduler.scheduler where id = ?";

        List<Scheduler> schedulerList = jdbcTemplate.query(sql,
                (rs, rowNum) -> Scheduler.builder()
                        .id(rs.getLong("id"))
                        .task(rs.getString("task"))
                        .member(memberRepository.findById(rs.getLong("member_id"))
                                .orElseThrow(() -> new HttpServerErrorException(HttpStatus.NOT_FOUND)))
                        .password(rs.getString("password"))
                        .createdAt(rs.getTimestamp("createdAt").toLocalDateTime())
                        .modifiedAt(rs.getTimestamp("modifiedAt").toLocalDateTime())
                        .build(), id);

        return schedulerList.stream().findAny();
    }

    @Override
    public List<Scheduler> findAll() {
        String sql = "select * from scheduler.scheduler order by modifiedAt desc";

        return jdbcTemplate.query(sql,
                (rs, rowNum) -> Scheduler.builder()
                        .id(rs.getLong("id"))
                        .task(rs.getString("task"))
                        .member(memberRepository.findById(rs.getLong("member_id"))
                                .orElseThrow(() -> new HttpServerErrorException(HttpStatus.NOT_FOUND)))
                        .password(rs.getString("password"))
                        .createdAt(rs.getTimestamp("createdAt").toLocalDateTime())
                        .modifiedAt(rs.getTimestamp("modifiedAt").toLocalDateTime())
                        .build());
    }

    @Override
    public List<Scheduler> findAllByName(String name) {
        String sql = "select * " +
                "from scheduler.scheduler s " +
                "join scheduler.member m on m.id = s.member_id " +
                "where m.name = ? " +
                "order by s.modifiedAt desc";

        return jdbcTemplate.query(sql,
                (rs, rowNum) -> Scheduler.builder()
                        .id(rs.getLong("id"))
                        .task(rs.getString("task"))
                        .member(memberRepository.findById(rs.getLong("member_id"))
                                .orElseThrow(() -> new HttpServerErrorException(HttpStatus.NOT_FOUND)))
                        .password(rs.getString("password"))
                        .createdAt(rs.getTimestamp("createdAt").toLocalDateTime())
                        .modifiedAt(rs.getTimestamp("modifiedAt").toLocalDateTime())
                        .build(), name);
    }

    @Override
    public List<Scheduler> findAllByModifiedAt(LocalDate modifiedAt) {
        String sql = "select * from scheduler.scheduler where date(scheduler.modifiedAt) = ? order by modifiedAt desc";

        return jdbcTemplate.query(sql,
                (rs, rowNum) -> Scheduler.builder()
                        .id(rs.getLong("id"))
                        .task(rs.getString("task"))
                        .member(memberRepository.findById(rs.getLong("member_id"))
                                .orElseThrow(() -> new HttpServerErrorException(HttpStatus.NOT_FOUND)))
                        .password(rs.getString("password"))
                        .createdAt(rs.getTimestamp("createdAt").toLocalDateTime())
                        .modifiedAt(rs.getTimestamp("modifiedAt").toLocalDateTime())
                        .build(), modifiedAt);
    }

    @Override
    public List<Scheduler> findAllByNameAndModifiedAt(String name, LocalDate modifiedAt) {
        String sql = "select * " +
                "from scheduler.scheduler s " +
                "join scheduler.member m on m.id = s.member_id " +
                "where m.name = ? and date(s.modifiedAt) = ? " +
                "order by s.modifiedAt desc";

        return jdbcTemplate.query(sql,
                (rs, rowNum) -> Scheduler.builder()
                        .id(rs.getLong("id"))
                        .task(rs.getString("task"))
                        .member(memberRepository.findById(rs.getLong("member_id"))
                                .orElseThrow(() -> new HttpServerErrorException(HttpStatus.NOT_FOUND)))
                        .password(rs.getString("password"))
                        .createdAt(rs.getTimestamp("createdAt").toLocalDateTime())
                        .modifiedAt(rs.getTimestamp("modifiedAt").toLocalDateTime())
                        .build(), name, modifiedAt);
    }


    @Override
    public Optional<Scheduler> updateScheduler(UpdateSchedulerRequestDto dto) {
        String sql = "update scheduler.scheduler s " +
                "join scheduler.member m on m.id = s.member_id " +
                "set s.task=?,m.name=? where s.id=? and s.password=?";
        int row = jdbcTemplate.update(sql,
                dto.getTask(), dto.getMemberName(), dto.getSchedulerId(), dto.getPassword());
        if (row > 0) {
            return findById(dto.getSchedulerId());
        } else {
            throw new HttpServerErrorException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Optional<Scheduler> deleteSchedulerByIdAndPassword(DeleteSchedulerRequestDto dto) {
        String sql = "delete from scheduler.scheduler where id=? and password=?";
        Optional<Scheduler> scheduler = findById(dto.getId());

        int row = jdbcTemplate.update(sql, dto.getId(), dto.getPassword());
        if (row > 0) {
            return scheduler;
        } else {
            throw new HttpServerErrorException(HttpStatus.NOT_FOUND);
        }
    }

}
