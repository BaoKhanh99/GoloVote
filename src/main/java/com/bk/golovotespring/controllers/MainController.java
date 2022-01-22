package com.bk.golovotespring.controllers;

import com.bk.golovotespring.entity.*;
import com.bk.golovotespring.repository.AccountRepository;
import com.bk.golovotespring.repository.BlockchainRepository;
import com.bk.golovotespring.repository.UserBlockRepository;
import com.bk.golovotespring.service.CandidateService;
import com.bk.golovotespring.service.PositionService;
import com.bk.golovotespring.service.UserVoteService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @Autowired
    UserBlockRepository userBlockRepository;

    @Autowired
    BlockchainRepository blockchainRepository;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/home-page")
    public String homePage(Model model) {
        model.addAttribute(new Account());
        return "views/home-page";
    }

//    @Autowired
//    BlockchainService blockchainService;

    @GetMapping("/position-list")
    public String positionListPage( HttpServletRequest req, Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        Account account = new Account();
        if (principal instanceof UserDetails) {
             username = ((UserDetails)principal).getUsername();
             account = accountRepository.findByUsername(username);

        }

        HttpSession session = req.getSession();
        session.setAttribute("userID", account.getIdAccount());
        session.setAttribute("userName", account.getName());
        int idAccount = account.getIdAccount();

        UserBlock userBlock = userBlockRepository.findUserBlockByAccount_IdAccount(idAccount);

        if (userBlock != null){
            return "redirect:/voted-list";
        }

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
        model.addAttribute("userVoteList",userVoteList);
        return "views/positions-list";
    }

    @Autowired
    PositionService positionService;

    @Autowired
    CandidateService candidateService;

    @GetMapping("/candidate-list/{idPosition}")
    public String CandidateListPage(@PathVariable("idPosition")String idPosition, Model model){
        Position position = positionService.findPositionById(Integer.parseInt(idPosition));
        List<Candidate> candidateList = candidateService.findAll();

        model.addAttribute("position", position);
        model.addAttribute("candidateList", candidateList);



        //model.addAttribute("resultSet",resultSet);

        return "views/candidate-list";
    }

    @GetMapping("/vote")
    public String voteCandidate(@RequestParam("idCandidate") String idCandidate, @RequestParam("idPosition") String idPosition, HttpServletRequest request){

        HttpSession session = request.getSession();

        UserVote userVote = new UserVote( new Account((Integer) session.getAttribute("userID")), new Position(Integer.parseInt(idPosition)), new Candidate(Integer.parseInt(idCandidate)));
        userVoteService.save(userVote);

        return "redirect:/position-list";
    }

    @PostMapping("/confirm-vote")
    public String SuccessfulVotePage(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();
        int idUser = (int) session.getAttribute("userID");

        int prefix = 4;
        String prefixString = new String(new char[prefix]).replace('\0', '0');
        List<UserVote> userVoteList = userVoteService.findUserVoteByAccount_IdAccount(idUser);

        int i = 0;
        Ballot ballot = new Ballot();
        List<Ballot> ballotList = new ArrayList<>();

        ballot.setIdUser(idUser);

        UserVote userVote = null;
        while (i < userVoteList.size()) {
            userVote = userVoteList.get(i);
            ballot.setIdCandidate(userVote.getCandidate().getId());
            ballot.setIdPosition(userVote.getPosition().getId());
            ballotList.add(ballot);
            i++;
        }

        UserBlock getLastBlock = userBlockRepository.findFirstByOrderByIdAsc();
        String previousHash = getLastBlock==null ? "0" : getLastBlock.getHash();

        String jsonBallot = new Gson().toJson(ballotList);
        Block newBlock = new Block(jsonBallot, previousHash);
        newBlock.mineBlock(prefix);
        String jsonBlock = new Gson().toJson(newBlock);

        if (newBlock.getHash().substring(0, prefix).equals(prefixString)) {
            blockchainRepository.save(new Blockchain(jsonBlock));
            UserBlock userBlock = new UserBlock(newBlock.getHash(),userVote.getAccount(),new Blockchain(jsonBlock));
            userBlockRepository.save(userBlock);
        }
        ;
        model.addAttribute("userVotes", userVoteList);
        model.addAttribute("block", newBlock);

        return "views/successful-vote-page";
    }

    @GetMapping("/voted-list")
    public String votedList(HttpServletRequest request, Model model){
        HttpSession session = request.getSession();
        int idUser = (int) session.getAttribute("userID");

        UserBlock userBlock = userBlockRepository.findUserBlockByAccount_IdAccount(idUser);
        Block blockFromJson= new Gson().fromJson(userBlock.getBlockchain().getBlock(), Block.class);

        List<UserVote> userVotes = userVoteService.findUserVoteByAccount_IdAccount(idUser);
        List<TotalVote> totalVotes = new ArrayList<>();
        for (int i = 0; i < userVotes.size(); i++) {
            List<TotalVote> resultSet = userVoteService.findAllTotalVoteByPosition(i+1);
            for (TotalVote totalVote: resultSet) {
                if (userVotes.get(i).getCandidate().getId() == totalVote.getIdCandidate()){
                    totalVotes.add(totalVote);
                }
            }
        }

        model.addAttribute("block", blockFromJson);
        model.addAttribute("totalVotes", totalVotes);

        return "views/voted-list";
    }

    @GetMapping("/ranking-page")
    public String rankingPage(Model model){

        List<TotalVote> totalVoteCaptain = userVoteService.findAllTotalVoteByPosition(1);
        List<TotalVote> totalVoteViceMonitor = userVoteService.findAllTotalVoteByPosition(2);
        List<TotalVote> totalVoteArt = userVoteService.findAllTotalVoteByPosition(3);

        model.addAttribute("totalVoteCaptain", totalVoteCaptain);
        System.out.println("============================= "+totalVoteCaptain.get(0).getCandidateName());
        model.addAttribute("totalVoteViceMonitor", totalVoteViceMonitor);
        System.out.println("============================= "+totalVoteViceMonitor.get(0).getCandidateName());
        model.addAttribute("totalVoteArt", totalVoteArt);
        System.out.println("============================= "+totalVoteArt.get(0).getCandidateName());

        return "views/ranking-page";
    }

    @GetMapping("/candidate-detail")
    public String candidateDetail(){

        return "views/";
    }
}
