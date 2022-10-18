package com.root.pilot.board.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.root.pilot.board.domain.Posts;
import com.root.pilot.board.repository.PostsRepository;
import com.root.pilot.board.dto.PostsSaveRequestDto;
import com.root.pilot.board.dto.PostsUpdateRequestDto;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
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
    public void TestRegisterPosts() {
        String title = "title";
        String content = "content";
        PostsSaveRequestDto requestDto =
            PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author(11L)
                .build();

        String url = "http://localhost:" + port + "/board";

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

    @Test
    public void TestUpdatePosts() {
        Posts savedPosts = postsRepository.save(Posts.builder()
            .title("title")
            .content("content")
            .author(11L)
            .build());

        Long updateId = savedPosts.getId();
        String updateTitle = "title2";
        String updateContent = "content2";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
            .title(updateTitle)
            .content(updateContent)
            .build();

        String url = "http://localhost:" + port + "/board/"+updateId;

        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        ResponseEntity<Long> responseEntity =
            restTemplate.exchange(url, HttpMethod.PATCH, requestEntity, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();

        assertThat(all.get(0).getTitle()).isEqualTo(updateTitle);
        assertThat(all.get(0).getContent()).isEqualTo(updateContent);
    }

    @Test
    public void TestDeletePosts() {
        Posts savedPosts = postsRepository.save(Posts.builder()
            .title("title")
            .content("content")
            .author(11L)
            .build());

        Long deleteId = savedPosts.getId();

        String url = "http://localhost:" + port + "/board/"+deleteId;

        ResponseEntity<Long> responseEntity =
            restTemplate.exchange(url, HttpMethod.DELETE, null, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();

        assertThat(all.size()).isEqualTo(0);

    }
}