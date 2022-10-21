package com.root.pilot.board.controller;

import com.root.pilot.board.dto.PageRequestDto;
import com.root.pilot.board.dto.PostsListWithPageResponseDto;
import com.root.pilot.board.dto.PostsResponseDto;
import com.root.pilot.board.dto.PostsSaveRequestDto;
import com.root.pilot.board.dto.PostsUpdateRequestDto;
import com.root.pilot.board.service.PostsService;
import com.root.pilot.security.dto.CustomUserDetails;
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
public class PostsApiController {

    private final PostsService postsService;

    @GetMapping
    public ResponseEntity<PostsListWithPageResponseDto> getList(PageRequestDto pageRequestDto) {
        PostsListWithPageResponseDto responseDto = postsService.getListWithPaging(pageRequestDto);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostsResponseDto> getPost(@PathVariable Long id) {
        PostsResponseDto responseDto = postsService.findById(id);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> save(@RequestBody PostsSaveRequestDto requestDto,
        @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long savedId = postsService.save(requestDto, userDetails.getId());

        return new ResponseEntity<>(savedId, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Long> update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {

        Long updatedId = postsService.update(id, requestDto);

        return new ResponseEntity<>(updatedId, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {

        Long deletedId = postsService.delete(id);

        return new ResponseEntity<>(deletedId, HttpStatus.OK);
    }







}
