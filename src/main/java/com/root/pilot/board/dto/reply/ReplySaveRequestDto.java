package com.root.pilot.board.dto.reply;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReplySaveRequestDto {
    @NotBlank(message = "내용을 입력해주세요.")
    @Size(max = 255, message = "255자 이내로 입력 가능합니다.")
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
