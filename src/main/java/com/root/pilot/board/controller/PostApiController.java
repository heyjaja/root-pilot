package com.root.pilot.board.controller;

import com.root.pilot.board.dto.PageRequestDto;
import com.root.pilot.board.dto.PostListWithPageResponseDto;
import com.root.pilot.board.dto.PostResponseDto;
import com.root.pilot.board.dto.PostSaveRequestDto;
import com.root.pilot.board.dto.PostUpdateRequestDto;
import com.root.pilot.board.service.PostService;
import com.root.pilot.security.dto.CustomUserDetails;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostApiController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<PostListWithPageResponseDto> getList(PageRequestDto pageRequestDto) {
        PostListWithPageResponseDto responseDto =
            postService.getListWithPaging(pageRequestDto.getPageable(), pageRequestDto.getKeyword());

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long id) {
        PostResponseDto responseDto = postService.findById(id);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> save(@Valid @RequestBody PostSaveRequestDto requestDto) {

        Long savedId = postService.save(requestDto);

        return new ResponseEntity<>(savedId, HttpStatus.OK);

    }

    @PatchMapping("/{id}")
    public ResponseEntity<Long> update(
        @PathVariable Long id, @Valid @RequestBody PostUpdateRequestDto requestDto) {

        Long updatedId = postService.update(id, requestDto);

        return new ResponseEntity<>(updatedId, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(
        @PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long deletedId = postService.delete(id, userDetails.getId());

        return new ResponseEntity<>(deletedId, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<PostListWithPageResponseDto> getListByUserId(
        @PathVariable Long userId, PageRequestDto pageRequestDto) {
        PostListWithPageResponseDto responseDto =
            postService.findPostsByUser(pageRequestDto.getPageable(), userId);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);

    }







}
