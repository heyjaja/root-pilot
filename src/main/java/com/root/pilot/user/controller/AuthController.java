package com.root.pilot.user.controller;

import com.root.pilot.security.dto.CustomUserDetails;
import com.root.pilot.user.dto.LoginRequestDto;
import com.root.pilot.user.dto.AuthResponseDto;
import com.root.pilot.user.dto.SignUpRequestDto;
import com.root.pilot.user.dto.UsersResponseDto;
import com.root.pilot.user.service.AuthService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService usersService;

    @PostMapping("/signup")
    public ResponseEntity<Long> registerUser(@Valid @RequestBody SignUpRequestDto requestDto) {

        Long id = usersService.registerUser(requestDto);

        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponseDto> authenticateUser(@Valid @RequestBody LoginRequestDto requestDto) {

        AuthResponseDto responseDto = usersService.signInUser(requestDto);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<UsersResponseDto> getUser(@AuthenticationPrincipal CustomUserDetails userDetails) {

        UsersResponseDto responseDto = UsersResponseDto.builder().userId(userDetails.getId())
            .userId(userDetails.getId())
            .name(userDetails.getName())
            .email(userDetails.getEmail()).build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }




}
