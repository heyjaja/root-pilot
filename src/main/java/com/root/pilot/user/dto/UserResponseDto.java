package com.root.pilot.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private Long userId;
    private String name;
    private String email;

    @Builder
    public UserResponseDto(Long userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }
}
