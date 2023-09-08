package com.root.pilot.board.service;

import com.root.pilot.board.domain.Post;
import com.root.pilot.board.domain.Reply;
import com.root.pilot.board.dto.reply.ReplySaveRequestDto;
import com.root.pilot.board.repository.PostRepository;
import com.root.pilot.board.repository.ReplyQueryRepository;
import com.root.pilot.board.repository.ReplyRepository;
import com.root.pilot.user.domain.AuthProvider;
import com.root.pilot.user.domain.Role;
import com.root.pilot.user.domain.User;
import com.root.pilot.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReplyServiceTest {

    // 실제 객체 대신에 사용할 객체
    @Mock
    ReplyRepository replyRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    PostRepository postRepository;

    @Mock
    ReplyQueryRepository replyQueryRepository;

    // mock 객체를 의존하는 객체
    @InjectMocks
    ReplyService replyService;

    @Test
    void TestSaveReply() {
        Long id = 1L;
        String content = "댓글입니다.";
        String username = "test";
        String postTitle = "안녕하세요.";


        User user = User.builder()
                .id(id)
                .email("test@test.test")
                .name(username)
                .authProvider(AuthProvider.local)
                .picture(null)
                .role(Role.ROLE_USER)
                .password("password")
                .build();
        Post post = Post.builder()
                .id(id)
                .title(postTitle)
                .content("반갑습니다.")
                .user(user)
                .build();
        Reply reply = Reply.builder()
                .id(id)
                .user(user)
                .post(post)
                .content(content)
                .build();

        ReplySaveRequestDto requestDto = ReplySaveRequestDto.builder()
                .content(content)
                .postId(id)
                .userId(id)
                .build();

        when(replyRepository.save(any())).thenReturn(reply);
        when(userRepository.findById(id)).thenReturn(Optional.ofNullable(user));
        when(postRepository.findById(id)).thenReturn(Optional.ofNullable(post));

        replyService.save(requestDto);

        assertThat(reply.getId()).isEqualTo(id);
        assertThat(reply.getContent()).isEqualTo(content);
        assertThat(reply.getUser().getName()).isEqualTo(username);
        assertThat(reply.getPost().getTitle()).isEqualTo(postTitle);
    }

}