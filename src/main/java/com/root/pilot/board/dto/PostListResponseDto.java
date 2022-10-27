package com.root.pilot.board.dto;

import com.root.pilot.board.domain.Post;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
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

}
