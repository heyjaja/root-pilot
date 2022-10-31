package com.root.pilot.board.controller;

import com.root.pilot.board.dto.reply.ReplyListResponseDto;
import com.root.pilot.board.dto.reply.ReplySaveRequestDto;
import com.root.pilot.board.service.ReplyService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
}
