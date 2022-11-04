package com.root.pilot.board.dto.reply;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ReplyUpdateRequestDto {
    private Long loginUserId;
    @NotBlank(message = "내용을 입력해주세요.")
    @Size(max = 255, message = "255자 이내로 입력 가능합니다.")
    private String content;
}
