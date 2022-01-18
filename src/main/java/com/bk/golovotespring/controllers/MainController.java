package com.bk.golovotespring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/home-page")
    public String homePage() {
        return "views/home-page";
    }

    @PostMapping("/login")
    public String loginPage(){

        return "fuck";
    }


}
