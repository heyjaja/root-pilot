package com.root.pilot.user.controller;

import com.root.pilot.user.dto.LoginRequestDto;
import com.root.pilot.user.dto.SignUpRequestDto;
import com.root.pilot.user.service.UsersService;
import java.net.URI;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsersService usersService;

    @PostMapping("/signup")
    public ResponseEntity<Long> registerUser(@Valid @RequestBody SignUpRequestDto requestDto) {

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/board").build().toUri();

        Long id = usersService.registerUser(requestDto);

        return ResponseEntity.created(location).body(id);
    }

    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@Valid @RequestBody LoginRequestDto requestDto) {
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/board").build().toUri();

        String token = usersService.signInUser(requestDto);

        return ResponseEntity.created(location).body(token);
    }

}
