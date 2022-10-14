package com.root.pilot.board.dto;

import com.root.pilot.board.domain.Posts;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostsListWithPageResponseDto {
    private List<PostsListResponseDto> postsList;
    private Long totalPages;
    private Long totalCount;

    @Builder
    public PostsListWithPageResponseDto(List<Posts> postsList, Long totalPages, Long totalCount) {
        this.postsList = postsList.stream()
            .map(PostsListResponseDto::new).collect(Collectors.toList());
        this.totalPages = totalPages;
        this.totalCount = totalCount;
    }

}
