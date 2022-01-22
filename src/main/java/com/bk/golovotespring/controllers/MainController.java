package com.bk.golovotespring.controllers;

import com.bk.golovotespring.entity.*;
import com.bk.golovotespring.repository.AccountRepository;
import com.bk.golovotespring.repository.BlockchainRepository;
import com.bk.golovotespring.repository.UserBlockRepository;
import com.bk.golovotespring.service.CandidateService;
import com.bk.golovotespring.service.PositionService;
import com.bk.golovotespring.service.UserVoteService;
import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Type;
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

        return "views/candidate-list";
    }

    @GetMapping("/vote")
    public String voteCandidate(@RequestParam("idCandidate") String idCandidate, @RequestParam("idPosition") String idPosition, HttpServletRequest request){

        HttpSession session = request.getSession();
        int idUser = (int) session.getAttribute("userID");

        UserVote checkUserVote = userVoteService.findTopByAccount_IdAccountAndPositionId(idUser, Integer.parseInt(idPosition));
        UserVote userVote;
        if (checkUserVote != null) {
            userVoteService.delete(checkUserVote);
        }
        userVote = new UserVote( new Account((Integer) session.getAttribute("userID")), new Position(Integer.parseInt(idPosition)), new Candidate(Integer.parseInt(idCandidate)));
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

        List<Ballot> ballotList = new ArrayList<>();
        Account account = userVoteList.get(0).getAccount();

        for (UserVote u: userVoteList) {
            Ballot ballot = new Ballot();
            ballot.setIdUser(idUser);
            ballot.setIdCandidate(u.getCandidate().getId());
            ballot.setIdPosition(u.getPosition().getId());
            ballotList.add(ballot);
        }

        UserBlock getLastBlock = userBlockRepository.findFirstByOrderByIdAsc();
        String previousHash = getLastBlock==null ? "0" : getLastBlock.getHash();

        String jsonBallot = new Gson().toJson(ballotList);
        Block newBlock = new Block(jsonBallot, previousHash);
        newBlock.mineBlock(prefix);
        String jsonBlock = new Gson().toJson(newBlock);

        System.out.println(jsonBlock);

        if (newBlock.getHash().substring(0, prefix).equals(prefixString)) {
            Blockchain blockchain = new Blockchain((jsonBlock));
            blockchainRepository.save(blockchain);
            UserBlock userBlock = new UserBlock(newBlock.getHash(),account, blockchain);
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

        if (userBlock == null){
            return "redirect:/position-list";
        }

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
    public String rankingPage(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        int idUser = (int) session.getAttribute("userID");

        if (userBlockRepository.findUserBlockByAccount_IdAccount(idUser) == null){
            return "redirect:/position-list";
        }

        List<TotalVote> totalVoteCaptain = userVoteService.findAllTotalVoteByPosition(1);
        List<TotalVote> totalVoteViceMonitor = userVoteService.findAllTotalVoteByPosition(2);
        List<TotalVote> totalVoteArt = userVoteService.findAllTotalVoteByPosition(3);

        model.addAttribute("totalVoteCaptain", totalVoteCaptain);
        model.addAttribute("totalVoteViceMonitor", totalVoteViceMonitor);
        model.addAttribute("totalVoteArt", totalVoteArt);

        return "views/ranking-page";
    }

    @GetMapping("/candidate-profile/{idCandidate}")
    public String candidateDetail(@PathVariable("idCandidate")String idCandidate, Model model){
        Candidate candidate = candidateService.findCandidateById(Integer.parseInt(idCandidate));
        model.addAttribute("candidate", candidate);

        return "views/candidate-profile";
    }

    @RequestMapping(value = "/search-block",method = RequestMethod.GET )
    public String searchBlock( Model model, @RequestParam("hashBlock")String hashBlock){

        UserBlock userBlock = userBlockRepository.findUserBlockByHash(hashBlock);
        Block blockFromJson = new Gson().fromJson(userBlock.getBlockchain().getBlock(), Block.class);

        Type typeMyType = new TypeToken<ArrayList<Ballot>>(){}.getType();
        List<Ballot> ballots = new Gson().fromJson(blockFromJson.getData(),typeMyType);

        List<TotalVote> totalVotes = userVoteService.findAllTotalVote();
        List<TotalVote> totalVotesFromBallot = new ArrayList<>();

        for (int i = 0; i < ballots.size(); i++) {
            System.out.println("=============================== "+ballots.get(i).getIdCandidate());
            for (TotalVote totalVote : totalVotes) {
                System.out.println("++++++++++++++++++++++++++++ "+totalVote.getCandidateName() );
                if (ballots.get(i).getIdCandidate() == totalVote.getIdCandidate()){
                    totalVotesFromBallot.add(totalVote);
                    break;
                }
            }
        }

        model.addAttribute("block",blockFromJson);
        model.addAttribute("totalVotes",totalVotesFromBallot);

        return "views/search-block";
    }
}
