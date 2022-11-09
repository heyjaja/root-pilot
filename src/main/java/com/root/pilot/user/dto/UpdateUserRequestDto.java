package com.root.pilot.user.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Getter
@NoArgsConstructor
@Slf4j
public class UpdateUserRequestDto {

    private Long userId;
    @NotBlank(message = "이름을 입력해주세요.")
    @Size(max = 20, min = 2, message = "이름은 2자 이상, 20자 이하로 입력해주세요.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9]*$", message = "이름은 한글, 영문, 숫자만 가능합니다.")
    private String name;

}
