package com.root.pilot.board.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.root.pilot.board.domain.Posts;
import com.root.pilot.board.repository.PostsRepository;
import com.root.pilot.board.dto.PostsSaveRequestDto;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class PostsServiceTest {

    @Autowired
    PostsService postsService;

    @Autowired
    PostsRepository postsRepository;

    @Test
    public void TestSavePostsService() {
        //given
        String title = "test title";
        String content = "test content";

        Long id = postsService.save(PostsSaveRequestDto.builder()
            .title(title)
            .content(content)
            .author(11L)
            .build());

        //when
        List<Posts> posts = postsRepository.findAll();

        Posts post = posts.get(0);
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);
        assertThat(post.getId().equals(id));

    }


}