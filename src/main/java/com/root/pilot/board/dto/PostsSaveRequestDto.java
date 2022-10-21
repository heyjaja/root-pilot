package com.root.pilot.board.dto;

import com.root.pilot.board.domain.Posts;
import com.root.pilot.user.domain.Users;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private String content;
    private Long user;

    @Builder
    public PostsSaveRequestDto(String title, String content, Long user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

}
