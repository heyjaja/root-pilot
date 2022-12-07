package com.root.pilot.board.repository;

import com.root.pilot.board.domain.Post;
import com.root.pilot.board.domain.Reply;
import com.root.pilot.user.domain.AuthProvider;
import com.root.pilot.user.domain.Role;
import com.root.pilot.user.domain.User;
import com.root.pilot.user.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ReplyRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private PostRepository postRepository;

    @Test
    public void initDb() {

        Optional<User> user1 = userRepository.findById(1L);
        Optional<User> user2 = userRepository.findById(2L);
        Optional<User> user3 = userRepository.findById(3L);
        Optional<User> user4 = userRepository.findById(4L);
        Optional<User> user5 = userRepository.findById(5L);

        Post post = Post.builder().title("reply please").content("댓글 테스트합니다<br>많이 달아주세요").user(user1.get()).build();

        postRepository.save(post);

        for(int i=1; i<=50; i++) {
            replyRepository.save(Reply.builder()
                .content("일요일 좋아 " + i)
                .user(user1.get())
                .post(post)
                .build());
            replyRepository.save(Reply.builder()
                .content("토요일 좋아 " + i)
                .user(user2.get())
                .post(post)
                .build());
            replyRepository.save(Reply.builder()
                .content("목요일 좋아 " + i)
                .user(user3.get())
                .post(post)
                .build());
            replyRepository.save(Reply.builder()
                .content("금요일 좋아 " + i)
                .user(user4.get())
                .post(post)
                .build());
            replyRepository.save(Reply.builder()
                .content("토요일 좋아 " + i)
                .user(user5.get())
                .post(post)
                .build());
        }


    }
}