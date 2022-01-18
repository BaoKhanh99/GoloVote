package com.bk.golovotespring.controllers;

import com.bk.golovotespring.entity.Account;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MainController {
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/home-page")
    public String homePage(Model model) {
        model.addAttribute(new Account());
        return "views/home-page";
    }

    @GetMapping("/position-list")
    public String positionListPage(Model model){
        Account account = new Account();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
             username = ((UserDetails)principal).getUsername();
        } else {
             username = principal.toString();
        }
        account.setUsername(username);
        model.addAttribute("account",account);
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
