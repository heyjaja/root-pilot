package com.root.pilot.board.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.root.pilot.board.domain.Posts;
import com.root.pilot.board.repository.PostsRepository;
import com.root.pilot.board.dto.PostsSaveRequestDto;
import com.root.pilot.security.dto.CustomUserDetails;
import com.root.pilot.user.domain.AuthProvider;
import com.root.pilot.user.domain.Role;
import com.root.pilot.user.domain.Users;
import com.root.pilot.user.repository.UsersRepository;
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

    @Autowired
    UsersRepository usersRepository;

    @Test
    public void TestSavePostsService() {
        //given
        String title = "test title";
        String content = "test content";

        Long id = postsService.save(PostsSaveRequestDto.builder()
            .title(title)
            .content(content)
            .user(1L)
            .build() , 1L);

        //when
        List<Posts> posts = postsRepository.findAll();

        Posts post = posts.get(0);
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);
        assertThat(post.getId().equals(id));

    }


}