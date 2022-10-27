package com.root.pilot.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class ExistCheckEmailRequestDto {

    @NotBlank(message = "이메일을 입력해주세요.")
    @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$",
        message = "이메일 형식에 맞게 작성해주세요.")
    private String email;

}
