package com.root.pilot.board.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.root.pilot.board.domain.Post;
import com.root.pilot.board.dto.PostResponseDto;
import com.root.pilot.board.repository.PostRepository;
import com.root.pilot.board.dto.PostSaveRequestDto;
import com.root.pilot.user.domain.AuthProvider;
import com.root.pilot.user.domain.Role;
import com.root.pilot.user.domain.User;
import com.root.pilot.user.repository.UserRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class PostServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    public void TestSavePostsService() {
        //given
        String title = "test title";
        String content = "test content";
        User user = User.builder().email("test@test.test").name("tester").password("123456")
            .role(Role.ROLE_USER).authProvider(AuthProvider.local).build();

        userRepository.save(user);

        //when
        Long id = postService.save(PostSaveRequestDto.builder()
            .title(title)
            .content(content)
            .userId(1L)
            .build());



        //then
        PostResponseDto post = postService.findById(id);
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);
        assertThat(post.getId().equals(id));

    }


}