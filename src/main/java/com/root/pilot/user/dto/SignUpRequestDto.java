package com.root.pilot.user.dto;

import com.root.pilot.user.domain.AuthProvider;
import com.root.pilot.user.domain.Role;
import com.root.pilot.user.domain.User;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class SignUpRequestDto {

    @NotBlank(message = "이메일을 입력해주세요.")
    @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$",
        message = "이메일 형식에 맞게 작성해주세요.")
    private String email;
    @NotBlank(message = "이름을 입력해주세요.")
    @Size(max = 20, min = 2, message = "이름은 2자 이상, 20자 이하로 입력해주세요.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9]*$", message = "이름은 한글, 영문, 숫자만 가능합니다.")
    private String name;
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(max = 20, min = 6, message = "비밀번호는 6자 이상, 20자 이하로 입력해주세요.")
    private String password;

    @NotBlank(message = "비밀번호를 확인해주세요.")
    private String password2;


    @Builder
    public SignUpRequestDto(String email, String name, String password, String password2) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.password2 = password2;
    }

    public User toEntity(String password) {
        return User.builder()
            .email(email)
            .name(name)
            .password(password)
            .authProvider(AuthProvider.local)
            .role(Role.ROLE_USER)
            .build();
    }

    //비밀번호 확인
    public Boolean validatePassword() {
        return password.equals(password2);
    }
}
