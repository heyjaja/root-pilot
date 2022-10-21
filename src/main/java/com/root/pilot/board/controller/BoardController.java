package com.root.pilot.board.controller;

import com.root.pilot.board.dto.PageRequestDto;
import com.root.pilot.board.service.PostsService;
import com.root.pilot.user.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController {
    private final PostsService postsService;

    @GetMapping
    public String getList(Model model, PageRequestDto pageRequestDto) {
        model.addAttribute("posts", postsService.getListWithPaging(pageRequestDto));

        return "board/index";
    }

    @GetMapping("/newpost")
    public String postsSave() {
        return "board/posts-save";
    }

    @GetMapping("/{id}")
    public String postsRead(Model model, @PathVariable Long id) {
        model.addAttribute("post", postsService.findById(id));

        return "board/posts-detail";
    }

    @GetMapping("/newpost/{id}")
    public String postsUpdate(Model model, @PathVariable Long id) {
        model.addAttribute("post", postsService.findById(id));

        return "board/posts-update";
    }

}
