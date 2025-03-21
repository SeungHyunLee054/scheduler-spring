package com.lsh.scheduler.module.scheduler.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lsh.scheduler.common.response.ListResponse;
import com.lsh.scheduler.module.scheduler.dto.SchedulerCreateRequestDto;
import com.lsh.scheduler.module.scheduler.dto.SchedulerDeleteRequestDto;
import com.lsh.scheduler.module.scheduler.dto.SchedulerResponseDto;
import com.lsh.scheduler.module.scheduler.dto.SchedulerUpdateRequestDto;
import com.lsh.scheduler.module.scheduler.service.SchedulerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SchedulerController.class)
class SchedulerControllerTest {
    @MockitoBean
    private SchedulerService schedulerService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("일정 생성 성공")
    @WithMockUser
    void success_createScheduler() throws Exception {
        // Given
        SchedulerCreateRequestDto dto = new SchedulerCreateRequestDto(1L, "test", "test");

        given(schedulerService.saveScheduler(any()))
                .willReturn(getResponseDto());

        // When
        ResultActions perform = mockMvc.perform(post("/schedulers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))
                .with(csrf()));

        // Then
        perform.andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id")
                                .value(1L),
                        jsonPath("$.name")
                                .value("test"),
                        jsonPath("$.task")
                                .value(dto.getTask())
                );

    }

    @Test
    @DisplayName("전체 조회 성공")
    @WithMockUser
    void success_getAllSchedulers() throws Exception {
        // Given
        LocalDate now = LocalDate.now();
        int pageIdx = 0;
        int pageSize = 10;
        List<SchedulerResponseDto> list = List.of(getResponseDto());

        given(schedulerService.getAllSchedulers(any(), any(), any()))
                .willReturn(ListResponse.<SchedulerResponseDto>builder()
                        .content(list).build());

        // When
        ResultActions perform1 = mockMvc
                .perform(get("/schedulers")
                        .param("pageIdx", Integer.toString(pageIdx))
                        .param("pageSize", Integer.toString(pageSize)));
        ResultActions perform2 = mockMvc
                .perform(get("/schedulers")
                        .param("name", "test")
                        .param("pageIdx", Integer.toString(pageIdx))
                        .param("pageSize", Integer.toString(pageSize)));
        ResultActions perform3 = mockMvc
                .perform(get("/schedulers")
                        .param("modifiedAt", now.toString())
                        .param("pageIdx", Integer.toString(pageIdx))
                        .param("pageSize", Integer.toString(pageSize)));
        ResultActions perform4 = mockMvc
                .perform(get("/schedulers")
                        .param("name", "test")
                        .param("modifiedAt", now.toString())
                        .param("pageIdx", Integer.toString(pageIdx))
                        .param("pageSize", Integer.toString(pageSize)));

        // Then
        perform1.andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.content.[0].id")
                                .value(1L),
                        jsonPath("$.content.[0].name")
                                .value("test"),
                        jsonPath("$.content.[0].task")
                                .value("test")
                );

        perform2.andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.content.[0].id")
                                .value(1L),
                        jsonPath("$.content.[0].name")
                                .value("test"),
                        jsonPath("$.content.[0].task")
                                .value("test")
                );

        perform3.andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.content.[0].id")
                                .value(1L),
                        jsonPath("$.content.[0].name")
                                .value("test"),
                        jsonPath("$.content.[0].task")
                                .value("test")
                );

        perform4.andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.content.[0].id")
                                .value(1L),
                        jsonPath("$.content.[0].name")
                                .value("test"),
                        jsonPath("$.content.[0].task")
                                .value("test")
                );

    }

    @Test
    @DisplayName("id로 일정 조회 성공")
    @WithMockUser
    void success_getSchedulerById() throws Exception {
        // Given
        Long schedulerId = 1L;
        given(schedulerService.getSchedulerById(anyLong()))
                .willReturn(getResponseDto());

        // When
        ResultActions perform = mockMvc
                .perform(get("/schedulers/{schedulerId}", schedulerId));

        // Then
        perform.andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id")
                                .value(1L),
                        jsonPath("$.name")
                                .value("test"),
                        jsonPath("$.task")
                                .value("test")
                );

    }

    @Test
    @DisplayName("일정 수정 성공")
    @WithMockUser
    void success_updateScheduler() throws Exception {
        // Given
        SchedulerUpdateRequestDto dto =
                new SchedulerUpdateRequestDto(1L, "test", "test", "test");

        given(schedulerService.updateScheduler(any()))
                .willReturn(getResponseDto());

        // When
        ResultActions perform = mockMvc.perform(put("/schedulers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))
                .with(csrf()));

        // Then
        perform.andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id")
                                .value(1L),
                        jsonPath("$.name")
                                .value("test"),
                        jsonPath("$.task")
                                .value("test")
                );
    }

    @Test
    @DisplayName("일정 삭제 성공")
    @WithMockUser
    void test() throws Exception {
        // Given
        SchedulerDeleteRequestDto dto = new SchedulerDeleteRequestDto(1L, "test");

        given(schedulerService.deleteScheduler(any()))
                .willReturn(getResponseDto());

        // When
        ResultActions perform = mockMvc.perform(delete("/schedulers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))
                .with(csrf()));

        // Then
        perform.andDo(print())
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id")
                                .value(1L),
                        jsonPath("$.name")
                                .value("test"),
                        jsonPath("$.task")
                                .value("test")
                );

    }


    private static SchedulerResponseDto getResponseDto() {
        LocalDateTime now = LocalDateTime.now();
        return SchedulerResponseDto.builder()
                .id(1L)
                .name("test")
                .task("test")
                .createdAt(now)
                .modifiedAt(now)
                .build();
    }

}