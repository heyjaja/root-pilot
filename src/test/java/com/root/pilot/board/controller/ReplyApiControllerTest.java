package com.root.pilot.board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.root.pilot.board.domain.Post;
import com.root.pilot.board.domain.Reply;
import com.root.pilot.board.dto.reply.ReplyListResponseDto;
import com.root.pilot.board.dto.reply.ReplySaveRequestDto;
import com.root.pilot.board.service.ReplyService;
import com.root.pilot.config.SecurityConfig;
import com.root.pilot.filter.TokenFilter;
import com.root.pilot.user.domain.AuthProvider;
import com.root.pilot.user.domain.Role;
import com.root.pilot.user.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ReplyApiController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE, classes = {TokenFilter.class, SecurityConfig.class}))
@AutoConfigureMockMvc(addFilters = false)
class ReplyApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ReplyService replyService;

    @Test
    @WithMockUser
    void testGetReplies() throws Exception {
        Long postId = 1L;
        Pageable pageable = PageRequest.of(0, 20);
        User user = User.builder()
                .id(1L)
                .email("test@test.test")
                .name("test")
                .authProvider(AuthProvider.local)
                .picture(null)
                .role(Role.ROLE_USER)
                .password("password")
                .build();
        Post post = Post.builder()
                .id(postId)
                .title("안녕하세요.")
                .content("반갑습니다.")
                .user(user)
                .build();
        List<Reply> list = new ArrayList<>();
        list.add(Reply.builder().user(user).post(post).content("댓글").build());
        ReplyListResponseDto response = ReplyListResponseDto.builder()
                .replies(list)
                .pageable(pageable)
                .totalCount(1L)
                .totalPages(1L)
                .build();

        // given: Mock 객체의 행위를 정의
        given(replyService.findReplyByPostId(postId, pageable))
                .willReturn(response);

        mockMvc.perform(
                get("/reply/" + postId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.replies").exists())
                .andDo(print());

        // verify: 해당 객체의 메서드가 실행되었는지 체크
        verify(replyService).findReplyByPostId(postId, pageable);

    }

    @Test
    @WithMockUser
    void TestSaveReply() throws Exception {

        ReplySaveRequestDto replyDto = ReplySaveRequestDto.builder()
                .content("댓글입니다.")
                .postId(1L)
                .userId(1L)
                .build();

        given(replyService.save(any())).willReturn(1L);

        String request = new ObjectMapper().writeValueAsString(replyDto);

        mockMvc.perform(
                post("/reply")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        verify(replyService).save(any());
    }


}