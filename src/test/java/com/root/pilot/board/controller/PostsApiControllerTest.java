package com.root.pilot.board.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.root.pilot.board.domain.Posts;
import com.root.pilot.board.domain.PostsRepository;
import com.root.pilot.board.dto.PostsSaveRequestDto;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class PostsApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @Test
    public void TestRegisterPosts() throws Exception {
        String title = "title";
        String content = "content";
        PostsSaveRequestDto requestDto =
            PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("tester")
                .build();

        String url = "http://localhost:" + port + "/posts";

        ResponseEntity<Long> responseEntity =
            restTemplate.postForEntity(url, requestDto, Long.class);

        assertThat(responseEntity.getStatusCode())
            .isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody())
            .isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }
}