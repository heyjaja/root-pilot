package com.root.pilot.board.dto.reply;

import com.root.pilot.board.domain.Reply;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ReplyResponseDto {
    private Long replyId;
    private String content;
    private String user;
    private Long userId;
    private LocalDateTime modifiedDate;

    public ReplyResponseDto(Reply reply) {
        this.replyId = reply.getId();
        this.content = reply.getContent();
        this.user = reply.getUser().getName();
        this.userId = reply.getUser().getId();
        this.modifiedDate = reply.getModifiedDate();
    }

}
