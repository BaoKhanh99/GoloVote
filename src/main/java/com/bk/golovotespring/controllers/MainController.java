package com.bk.golovotespring.controllers;

import com.bk.golovotespring.entity.Account;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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

    @PostMapping("/login")
    public String loginPage(){

        return "fuck";
    }
    @PostMapping("/account/login")
    public void doLogin(@ModelAttribute Account account){
        System.out.println("account: "+account.getUsername());
    }


}
