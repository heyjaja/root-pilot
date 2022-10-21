package com.root.pilot.user.dto;

import com.root.pilot.user.domain.AuthProvider;
import com.root.pilot.user.domain.Role;
import com.root.pilot.user.domain.Users;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
public class SignUpRequestDto {

    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z-_]{2,20}$")
    private String name;
    @NotBlank
    @Length(max = 20, min = 6)
    private String password;

    @Builder
    public SignUpRequestDto(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public Users toEntity(String password) {
        return Users.builder()
            .email(email)
            .name(name)
            .password(password)
            .authProvider(AuthProvider.local)
            .role(Role.ROLE_USER)
            .build();
    }
}
