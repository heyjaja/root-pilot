package com.root.pilot.board.controller;

import com.root.pilot.board.dto.PageRequestDto;
import com.root.pilot.board.dto.UserPostsResponseDto;
import com.root.pilot.board.service.PostQueryService;
import com.root.pilot.commons.Timer;
import com.root.pilot.security.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/userpost")
public class UserPostApiController {

    private final PostQueryService postQueryService;

    @Timer
    @GetMapping("/{userId}")
    public ResponseEntity<UserPostsResponseDto> getListByUserIdNoOffset(
        @PathVariable Long userId, Long postId,
        PageRequestDto pageRequestDto,
        @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        if(userId != customUserDetails.getId()) {
        throw new IllegalArgumentException("사용자 정보가 일치하지 않습니다.");
        }

        UserPostsResponseDto responseDto =
            postQueryService.getUserPosts(postId, userId, pageRequestDto.getPageable(), pageRequestDto.getKeyword());

        return new ResponseEntity<>(responseDto, HttpStatus.OK);

    }
}
