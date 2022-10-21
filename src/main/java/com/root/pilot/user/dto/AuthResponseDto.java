package com.root.pilot.user.dto;

import lombok.Getter;

@Getter
public class AuthResponseDto {

    private String accessToken;

    public AuthResponseDto(String token) {
        this.accessToken = token;
    }
}
