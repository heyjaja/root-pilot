package com.root.pilot.user.dto;

import com.root.pilot.user.domain.User;
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

    public UserResponseDto(User user) {
        this.userId = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
    }
}
