package com.root.pilot.board.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.root.pilot.board.domain.Post;
import com.root.pilot.board.dto.PostListResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("local")
class PostQueryRepositoryTest {

    @Autowired
    private PostQueryRepository postQueryRepository;

    @Test
    public void TestGetPostsDtoList() {
    }

    @Test
    public void TestToIdsQuery() {
        postQueryRepository.toPostIds(1L, 10, null);
    }

    @Test
    public void TestSimplePaging() {
        Page<Post> posts = postQueryRepository.getPostsList(PageRequest.of(10000000, 10), null);

        assertThat(posts.getContent()).hasSize(10);

    }

    @Test
    public void TestCoveringPaging() {
        Page<Post> posts = postQueryRepository.getPostsCoveringIndexList(PageRequest.of(10000000, 10), null);

        assertThat(posts.getContent()).hasSize(10);

    }

    @Test
    public void TestCoveringPagingDto() {
        Page<PostListResponseDto> posts = postQueryRepository.getPostsDtoList(PageRequest.of(1, 10), null);

        assertThat(posts.getContent()).hasSize(10);

    }
}