package com.root.pilot.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequestDto {

    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;

}
