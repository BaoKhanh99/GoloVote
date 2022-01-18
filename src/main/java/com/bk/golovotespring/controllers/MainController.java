package com.bk.golovotespring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/position-list")
    public String positionListPage(){
        return "views/positions-list";
    }

    @GetMapping("/candidate-list/{position}")
    public String CandidateListPage(@PathVariable("position")String position){
        return "views/candidate-list";
    }

    @GetMapping("/successful-vote-page")
    public String SuccessfulVotePage(){
        return "views/successful-vote-page";
    }

    @GetMapping("/voted-list")
    public String votedList(){
        return "views/voted-list";
    }

    @GetMapping("/ranking-page")
    public String rankingPage(){
        return "views/ranking-page";
    }
}
