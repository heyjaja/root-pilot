package com.root.pilot.board.controller;

import com.root.pilot.board.dto.PageRequestDto;
import com.root.pilot.board.dto.PostListWithPageResponseDto;
import com.root.pilot.board.service.PostQueryService;
import com.root.pilot.commons.Timer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController {

    private final PostQueryService postQueryService;

    @Timer
    @GetMapping
    public String getList(Model model, PageRequestDto pageRequestDto) {

        PostListWithPageResponseDto responseDto =
            postQueryService.getDtoListWithPaging(pageRequestDto.getPageable(), pageRequestDto.getKeyword());
            //postQueryService.getListWithPaging(pageRequestDto.getPageable(),pageRequestDto.getKeyword());
            //postQueryService.getListCoveringIndexWithPaging(pageRequestDto.getPageable(),pageRequestDto.getKeyword());
        model.addAttribute("posts", responseDto);

        return "board/list";
    }

    @GetMapping("/newpost")
    public String postsSave() {
        return "board/posts-save";
    }

    @GetMapping("/{id}")
    public String postsRead() {
        return "board/posts-detail";
    }

    @GetMapping("/newpost/{id}")
    public String postsUpdate() {
        return "board/posts-update";
    }

    @GetMapping("/mypost")
    public String myPost() { return "board/mypost"; }

}
