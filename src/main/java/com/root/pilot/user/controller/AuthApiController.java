package com.root.pilot.user.controller;

import com.root.pilot.user.dto.ExistCheckEmailRequestDto;
import com.root.pilot.user.dto.ExistCheckNameRequestDto;
import com.root.pilot.user.dto.LoginRequestDto;
import com.root.pilot.user.dto.AuthResponseDto;
import com.root.pilot.user.dto.SignUpRequestDto;
import com.root.pilot.user.dto.ValidResponseDto;
import com.root.pilot.user.service.AuthService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthApiController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Long> registerUser(@Valid @RequestBody SignUpRequestDto requestDto) {

        Long id = authService.registerUser(requestDto);

        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponseDto> authenticateUser(@Valid @RequestBody LoginRequestDto requestDto) {

        AuthResponseDto responseDto = authService.signInUser(requestDto);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping("/signup/exists-name")
    public ResponseEntity<ValidResponseDto> validateName(@Valid @RequestBody ExistCheckNameRequestDto requestDto) {
        authService.existsByName(requestDto.getName());

        return new ResponseEntity<>(new ValidResponseDto("사용할 수 있는 이름입니다."), HttpStatus.OK);
    }

    @PostMapping("/signup/exists-email")
    public ResponseEntity<ValidResponseDto> validateEmail(@Valid @RequestBody ExistCheckEmailRequestDto requestDto) {
        authService.existsByEmail(requestDto.getEmail());

        return new ResponseEntity<>(new ValidResponseDto("사용할 수 있는 이메일입니다."), HttpStatus.OK);
    }

}
