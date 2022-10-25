package com.root.pilot.user.controller;

import com.root.pilot.security.dto.CustomUserDetails;
import com.root.pilot.user.dto.UserResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping()
    public ResponseEntity<UserResponseDto> getLoginUser(@AuthenticationPrincipal CustomUserDetails userDetails) {

        UserResponseDto responseDto = UserResponseDto.builder()
            .userId(userDetails.getId())
            .name(userDetails.getName())
            .email(userDetails.getEmail()).build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
