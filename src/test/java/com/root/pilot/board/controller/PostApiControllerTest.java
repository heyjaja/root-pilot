package com.root.pilot.board.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.root.pilot.board.domain.Post;
import com.root.pilot.board.dto.PostSaveRequestDto;
import com.root.pilot.board.dto.PostUpdateRequestDto;
import com.root.pilot.board.repository.PostRepository;
import com.root.pilot.user.domain.AuthProvider;
import com.root.pilot.user.domain.Role;
import com.root.pilot.user.domain.User;
import com.root.pilot.user.repository.UserRepository;
import java.util.List;
import java.util.Optional;
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
class PostApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void TestRegisterPosts() {
        String title = "title";
        String content = "content";

        User user = User.builder().email("test@test.test").name("tester").password("test11")
            .role(Role.ROLE_USER).authProvider(AuthProvider.local).build();

        userRepository.save(user);

        PostSaveRequestDto requestDto =
            PostSaveRequestDto.builder()
                .title(title)
                .content(content)
                .userId(1L)
                .build();

        String url = "http://localhost:" + port + "/board";

        ResponseEntity<Long> responseEntity =
            restTemplate.postForEntity(url, requestDto, Long.class);

        assertThat(responseEntity.getStatusCode())
            .isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody())
            .isGreaterThan(0L);

        Optional<Post> post = postRepository.findById(responseEntity.getBody());
        assertThat(post.get().getTitle()).isEqualTo(title);
        assertThat(post.get().getContent()).isEqualTo(content);
    }

    @Test
    public void TestUpdatePosts() {

        Post savedPosts = postRepository.save(Post.builder()
            .title("title")
            .content("content")
            .build());

        Long updateId = savedPosts.getId();
        String updateTitle = "title2";
        String updateContent = "content2";

        PostUpdateRequestDto requestDto = PostUpdateRequestDto.builder()
            .title(updateTitle)
            .content(updateContent)
            .build();

        String url = "http://localhost:" + port + "/board/"+updateId;

        HttpEntity<PostUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        ResponseEntity<Long> responseEntity =
            restTemplate.exchange(url, HttpMethod.PATCH, requestEntity, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Post> all = postRepository.findAll();

        assertThat(all.get(0).getTitle()).isEqualTo(updateTitle);
        assertThat(all.get(0).getContent()).isEqualTo(updateContent);
    }

    @Test
    public void TestDeletePosts() {

        User user = User.builder().email("test@test.test").name("tester").password("test11")
            .role(Role.ROLE_USER).authProvider(AuthProvider.local).build();

        userRepository.save(user);

        Post savedPosts = postRepository.save(Post.builder()
            .title("title")
            .content("content")
            .user(user)
            .build());

        Long deleteId = savedPosts.getId();

        String url = "http://localhost:" + port + "/board/"+deleteId;

        ResponseEntity<Long> responseEntity =
            restTemplate.exchange(url, HttpMethod.DELETE, null, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Post> all = postRepository.findAll();

        assertThat(all.size()).isEqualTo(0);

    }
}