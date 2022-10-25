package com.root.pilot.board.controller;

import com.root.pilot.board.dto.PageRequestDto;
import com.root.pilot.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController {
    private final PostService postService;

    @GetMapping
    public String getList(Model model, PageRequestDto pageRequestDto) {
        model.addAttribute("posts", postService.getListWithPaging(pageRequestDto));

        return "board/index";
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

}
