package com.root.pilot.board.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.root.pilot.board.repository.PostRepository;
import com.root.pilot.user.domain.AuthProvider;
import com.root.pilot.user.domain.Role;
import com.root.pilot.user.domain.User;
import com.root.pilot.user.repository.UserRepository;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @AfterEach
    public void cleanUp() {
        postRepository.deleteAll();
    }

    @Test
    @Rollback(value = false)
    public void TestSavePostAndGetList() {
        //given
        String title = "test title";
        String content = "test content";

        User user = User.builder().email("test@test.test").name("tester").password("123456")
                .role(Role.ROLE_USER).authProvider(AuthProvider.local).build();

        userRepository.save(user);

        Post requestPost = Post.builder().title(title).content(content).user(user).build();

        //when
        Post savedPost = postRepository.save(requestPost);


        //then
        assertThat(requestPost.getTitle()).isEqualTo(savedPost.getTitle());
        assertThat(requestPost.getContent()).isEqualTo(savedPost.getContent());
    }

}