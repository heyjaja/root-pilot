package com.root.pilot.user.dto;

import com.root.pilot.user.domain.AuthProvider;
import com.root.pilot.user.domain.Role;
import com.root.pilot.user.domain.Users;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SignUpRequestDto {

    private String email;
    private String name;
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
            .role(Role.USER)
            .build();
    }
}
