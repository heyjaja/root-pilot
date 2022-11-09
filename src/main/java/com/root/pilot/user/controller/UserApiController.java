package com.root.pilot.user.controller;

import com.root.pilot.commons.SuccessResponseDto;
import com.root.pilot.security.dto.CustomUserDetails;
import com.root.pilot.user.dto.UpdateUserRequestDto;
import com.root.pilot.user.dto.UserListResponseDto;
import com.root.pilot.user.dto.UserResponseDto;
import com.root.pilot.user.service.UserService;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserApiController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserResponseDto> getLoginUser(@AuthenticationPrincipal CustomUserDetails userDetails) {

        UserResponseDto responseDto = UserResponseDto.builder()
            .userId(userDetails.getId())
            .name(userDetails.getName())
            .email(userDetails.getEmail()).build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<UserListResponseDto> getUserList() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<SuccessResponseDto> updateUserName(@AuthenticationPrincipal CustomUserDetails userDetails,
        @RequestBody UpdateUserRequestDto requestDto) {
        
        if(userDetails.getId() != requestDto.getUserId()) {
            throw new EntityNotFoundException("본인만 수정할 수 있습니다.");
        }

        SuccessResponseDto responseDto = SuccessResponseDto.getResponse(userService.updateUserName(requestDto));
        
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
        
    }


}
