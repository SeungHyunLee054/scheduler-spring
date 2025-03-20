package com.lsh.scheduler.module.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lsh.scheduler.module.member.dto.MemberCreateRequestDto;
import com.lsh.scheduler.module.member.dto.MemberResponseDto;
import com.lsh.scheduler.module.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MemberController.class)
class MemberControllerTest {
    @MockitoBean
    private MemberService memberService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("유저 생성 성공")
    @WithMockUser
    void success_createMember() throws Exception {
        // Given
        MemberCreateRequestDto dto = new MemberCreateRequestDto("test", "test@test");
        given(memberService.createMember(any()))
                .willReturn(MemberResponseDto.builder()
                        .id(1L)
                        .name("test")
                        .email("test@test")
                        .build());

        // When
        ResultActions perform = mockMvc.perform(post("/members")
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
                                .value(dto.getName()),
                        jsonPath("$.email")
                                .value(dto.getEmail())
                );

    }
}