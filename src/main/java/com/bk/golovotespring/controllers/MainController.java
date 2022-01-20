package com.bk.golovotespring.controllers;

import com.bk.golovotespring.entity.Account;
import com.bk.golovotespring.entity.Candidate;
import com.bk.golovotespring.entity.Position;
import com.bk.golovotespring.entity.UserVote;
import com.bk.golovotespring.repository.AccountRepository;
import com.bk.golovotespring.service.CandidateService;
import com.bk.golovotespring.service.PositionService;
import com.bk.golovotespring.service.UserVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Controller
public class MainController {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserVoteService userVoteService;

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
    public String positionListPage( HttpServletRequest req, Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        Account account = new Account();
        if (principal instanceof UserDetails) {
             username = ((UserDetails)principal).getUsername();
             account = accountRepository.findByUsername(username);

        } else {
             username = principal.toString();
        }
        HttpSession session = req.getSession();
        session.setAttribute("userID", account.getIdAccount());
        session.setAttribute("userName", account.getName());
        int idAccount = account.getIdAccount();
        List<UserVote> userVoteList =  userVoteService.findUserVoteByAccount_IdAccount(idAccount);
        List<Position> positionList = new ArrayList<>();
        List<Candidate> candidateList = new ArrayList<>();

        for (UserVote userVote : userVoteList) {
            positionList.add(userVote.getPosition());
            candidateList.add(userVote.getCandidate());
        }

        List<Integer> idPositionList = new ArrayList<>();

        for (Position position: positionList) {
            idPositionList.add(position.getId());
        }

        model.addAttribute("idPosition", idPositionList);
        model.addAttribute("candidateList", candidateList);
        model.addAttribute(userVoteList);
        return "views/positions-list";
    }

    @Autowired
    PositionService positionService;

    @Autowired
    CandidateService candidateService;

    @GetMapping("/candidate-list/{idPosition}")
    public String CandidateListPage(@PathVariable("idPosition")String idPosition, Model model){
        Position position = positionService.findPositionById(Integer.parseInt(idPosition));
        List<Candidate> candidateList = candidateService.findCandidateByPositionId(Integer.parseInt(idPosition));

        model.addAttribute("position", position);
        model.addAttribute("candidateList", candidateList);

        return "views/candidate-list";
    }

    @GetMapping("/vote")
    public String voteCandidate(@RequestParam("idCandidate") String idCandidate, @RequestParam("idPosition") String idPosition, HttpServletRequest request){

        HttpSession session = request.getSession();

        UserVote userVote = new UserVote( new Account((Integer) session.getAttribute("userID")), new Position(Integer.parseInt(idPosition)), new Candidate(Integer.parseInt(idCandidate)));
        userVoteService.save(userVote);

        return "redirect:/position-list";
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
