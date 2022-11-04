package com.root.pilot.board.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.root.pilot.board.domain.Post;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PostListResponseDto {
    private Long postId;
    private String title;
    private String user;
    private LocalDateTime createdDate;

    public PostListResponseDto(Post entity) {
        this.postId = entity.getId();
        this.title = entity.getTitle();
        this.user = entity.getUser().getName();
        this.createdDate = entity.getCreatedDate();
    }

    @QueryProjection
    public PostListResponseDto(Long postId, String title, String user, LocalDateTime createdDate) {
        this.postId = postId;
        this.title = title;
        this.user = user;
        this.createdDate = createdDate;
    }

}
