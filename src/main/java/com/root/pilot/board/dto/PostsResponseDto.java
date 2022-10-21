package com.root.pilot.board.dto;

import com.root.pilot.board.domain.Posts;
import com.root.pilot.user.domain.Users;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Getter;

@Getter
public class PostsResponseDto {
    private Long id;
    private String title;
    private String content;
    private String user;
    private LocalDateTime modifiedDate;

    public PostsResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.user = entity.getUser().getName();
        this.modifiedDate = entity.getModifiedDate();
    }

}
