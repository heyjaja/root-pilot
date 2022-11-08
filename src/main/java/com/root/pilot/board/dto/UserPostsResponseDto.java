package com.root.pilot.board.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
public class UserPostsResponseDto {

    private List<PostListResponseDto> postsList;
    private boolean next;
    private Long lastPostId;

    @Builder
    public UserPostsResponseDto(List<PostListResponseDto> postsList, boolean next, Long lastPostId) {
        this.postsList = postsList;
        this.next = next;
        this.lastPostId = lastPostId;
    }

}
