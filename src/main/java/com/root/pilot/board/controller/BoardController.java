package com.root.pilot.board.controller;

import com.root.pilot.board.dto.PageRequestDto;
import com.root.pilot.board.dto.PostListWithPageResponseDto;
import com.root.pilot.board.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController {
    private final PostService postService;

    @GetMapping
    public String getList(Model model, PageRequestDto pageRequestDto) {

        PostListWithPageResponseDto responseDto =
            postService.getListWithPaging(pageRequestDto.getPageable(), pageRequestDto.getKeyword());
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

    @GetMapping("/mypage")
    public String myPage() { return "/mypage"; }

}
