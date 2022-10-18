package com.root.pilot.board.dto;

import com.root.pilot.board.domain.Posts;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

@Getter
public class PostsListWithPageResponseDto {
    private List<PostsListResponseDto> postsList;
    private Long totalPages;
    private Long totalCount;

    private int page;
    private int size;
    private int start, end;
    private boolean prev, next;

    private List<Integer> pageList;

    @Builder
    public PostsListWithPageResponseDto(List<Posts> postsList, Long totalPages, Long totalCount, Pageable pageable) {
        this.postsList = postsList.stream()
            .map(PostsListResponseDto::new).collect(Collectors.toList());
        this.totalPages = totalPages;
        this.totalCount = totalCount;
        makePageList(pageable);
    }

    public void makePageList(Pageable pageable) {
        this.page = pageable.getPageNumber() + 1;
        this.size = pageable.getPageSize();

        int tempEnd = (int) (Math.ceil(page/5.0)) * 5;

        start = tempEnd - 4;

        prev = start > 1;
        end = (int) Math.min(totalPages, tempEnd);

        next = totalPages > tempEnd;

        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }

}
