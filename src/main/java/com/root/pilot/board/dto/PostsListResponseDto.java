package com.root.pilot.board.dto;

import com.root.pilot.board.domain.Posts;
import com.root.pilot.user.domain.Users;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Getter;

@Getter
public class PostsListResponseDto {
    private Long id;
    private String title;
    private String user;
    private LocalDateTime modifiedDate;

    public PostsListResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.user = entity.getUser().getName();
        this.modifiedDate = entity.getModifiedDate();
    }

}
