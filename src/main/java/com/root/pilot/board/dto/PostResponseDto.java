package com.root.pilot.board.dto;

import com.root.pilot.board.domain.Post;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class PostResponseDto {
    private Long postId;
    private String title;
    private String content;
    private String user;
    private Long userId;
    private LocalDateTime createdDate;

    public PostResponseDto(Post entity) {
        this.postId = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.user = entity.getUser().getName();
        this.userId = entity.getUser().getId();
        this.createdDate = entity.getModifiedDate();
    }

}
