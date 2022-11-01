package com.root.pilot.board.dto.reply;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReplySaveRequestDto {
    @NotBlank(message = "내용을 입력해주세요.")
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
