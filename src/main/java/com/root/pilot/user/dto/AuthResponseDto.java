package com.root.pilot.user.dto;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class AuthResponseDto {

    private String accessToken;

    public AuthResponseDto(String token) {
        this.accessToken = token;
    }
}
