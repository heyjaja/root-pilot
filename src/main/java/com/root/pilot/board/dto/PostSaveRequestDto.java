package com.root.pilot.board.dto;

import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostSaveRequestDto {
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;
    private String content;
    private Long userId;

    @Builder
    public PostSaveRequestDto(String title, String content, Long userId) {
        this.title = title;
        this.content = content;
        this.userId = userId;
    }

}
