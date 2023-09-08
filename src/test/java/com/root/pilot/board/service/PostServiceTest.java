package com.root.pilot.board.service;

import com.root.pilot.board.dto.PostResponseDto;
import com.root.pilot.board.dto.PostSaveRequestDto;
import com.root.pilot.board.repository.PostRepository;
import com.root.pilot.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(post.getPostId().equals(id));

    }



}