package com.root.pilot.board.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.root.pilot.board.domain.Post;
import com.root.pilot.user.domain.User;
import com.root.pilot.user.repository.UserRepository;
import java.util.Optional;
import java.util.Random;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    @Rollback(value = false)
    public void TestSavePostAndGetList() {
        //given
        String title = "test title";
        String content = "test content";

        Optional<User> user = userRepository.findById(1L);

        Post requestPost = Post.builder().title(title).content(content).user(user.get()).build();

        //when
        Post savedPost = postRepository.save(requestPost);


        //then
        assertThat(requestPost.getTitle()).isEqualTo(savedPost.getTitle());
        assertThat(requestPost.getContent()).isEqualTo(savedPost.getContent());
    }

    @Test
    @Rollback(value = false)
    @Transactional
    public void initDb() {

        Optional<User> user = userRepository.findById(1L);
        Optional<User> user2 = userRepository.findById(2L);
        Optional<User> user3 = userRepository.findById(3L);
        Optional<User> user4 = userRepository.findById(4L);
        Optional<User> user5 = userRepository.findById(5L);




        for(int i=1; i<=50000; i++) {
            postRepository.save(Post.builder()
                .title(random())
                .content(random())
                .user(user.get())
                .build());
            postRepository.save(Post.builder()
                .title(random())
                .content(random())
                .user(user2.get())
                .build());
            postRepository.save(Post.builder()
                .title(random())
                .content(random())
                .user(user3.get())
                .build());
            postRepository.save(Post.builder()
                .title(random())
                .content(random())
                .user(user4.get())
                .build());
            postRepository.save(Post.builder()
                .title(random())
                .content(random())
                .user(user5.get())
                .build());

        }


    }

    public String random() {
        Random random = new Random();

        return random.ints(97, 123)
            .limit(10)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();
    }

}