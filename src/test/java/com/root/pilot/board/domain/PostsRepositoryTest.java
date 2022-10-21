package com.root.pilot.board.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.root.pilot.board.repository.PostsRepository;
import com.root.pilot.user.domain.AuthProvider;
import com.root.pilot.user.domain.Role;
import com.root.pilot.user.domain.Users;
import com.root.pilot.user.repository.UsersRepository;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @Autowired
    UsersRepository usersRepository;

    @AfterEach
    public void cleanUp() {
        postsRepository.deleteAll();
    }

    @Test
    public void TestSavePostAndGetList() {
        //given
        String title = "test title";
        String content = "test content";

        Users user = Users.builder().email("test@test.test").name("tester").password("123456")
                .role(Role.ROLE_USER).authProvider(AuthProvider.local).build();

        usersRepository.save(user);

        postsRepository.save(Posts.builder().title(title).content(content).user(user).build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

}