package com.root.pilot.board.controller;

import com.root.pilot.board.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class IndexController {
    private final PostsService postsService;

    @GetMapping("")
    public String list(Model model, @PageableDefault(size=3) Pageable pageable) {
        //model.addAttribute("posts", postsService.findAllDesc());
        model.addAttribute("posts", postsService.getListWithPaging(pageable));

        return "board/list";
    }

    @GetMapping("/newpost")
    public String postsSave() {
        return "/board/posts-save";
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
