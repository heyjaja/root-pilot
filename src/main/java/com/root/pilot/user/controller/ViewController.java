package com.root.pilot.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/signup")
    public String signupForm() {
        return "signup";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

}
