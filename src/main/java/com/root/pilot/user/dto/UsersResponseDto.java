package com.root.pilot.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class UsersResponseDto {
    private Long userId;
    private String name;
    private String email;

    @Builder
    public UsersResponseDto(Long userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }
}
