package com.root.pilot.board.controller;

import com.root.pilot.board.dto.PageRequestDto;
import com.root.pilot.board.dto.reply.ReplyListResponseDto;
import com.root.pilot.board.dto.reply.ReplySaveRequestDto;
import com.root.pilot.board.dto.reply.ReplyUpdateRequestDto;
import com.root.pilot.board.service.ReplyService;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/reply")
public class ReplyApiController {

    private final ReplyService replyService;

    @PostMapping
    public ResponseEntity<Long> save(@Valid @RequestBody ReplySaveRequestDto requestDto) {
        Long savedId = replyService.save(requestDto);

        return new ResponseEntity<>(savedId, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Long> update(
        @PathVariable Long id, @RequestBody ReplyUpdateRequestDto requestDto) {
        Long updatedId = replyService.update(id, requestDto);

        return new ResponseEntity<>(updatedId, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(
        @PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long deletedId = replyService.delete(id, userDetails.getId());

        return new ResponseEntity<>(deletedId, HttpStatus.OK);

    }

    @GetMapping("/{postId}")
    public ResponseEntity<ReplyListResponseDto> getList(
        @PathVariable Long postId, PageRequestDto pageRequestDto) {

        ReplyListResponseDto responseDto =
            replyService.findReplyByPostId(postId, pageRequestDto.getPageable());

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}
