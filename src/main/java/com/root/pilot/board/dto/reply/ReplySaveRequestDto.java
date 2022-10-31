package com.root.pilot.board.dto.reply;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReplySaveRequestDto {
    private String content;
    private Long userId;
    private Long postId;


    @Builder
    public ReplySaveRequestDto(String content, Long userId, Long postId) {
        this.content = content;
        this.userId = userId;
        this.postId = postId;
    }
}
