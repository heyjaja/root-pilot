package com.root.pilot.board.dto.reply;

import lombok.Getter;

@Getter
public class ReplyUpdateRequestDto {
    private Long loginUserId;
    private String content;
}
