package com.services;

import com.model.*;
import com.repositories.MasinaPunctControlRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by sergiubulzan on 22/06/2017.
 */
public class MasinaPunctControlService {
    MasinaPunctControlRepository repository;
    public MasinaPunctControlService(MasinaPunctControlRepository repository){
        this.repository = repository;
    }

    public void save(MasinaPunctControl masinaPunctControl) {
        this.repository.save(masinaPunctControl);
    }

    public MasinaPunctControl findOne(Integer id) {
        return this.repository.findOne(id);
    }

    public List<MasinaPunctControl> findAll() {
        return this.repository.findAll();
    }

//    List<TSUser> currentlyPlaying = new ArrayList<>();
//    List<TSUser> pending = new ArrayList<>();
//
//    List<Game> lastResults = new ArrayList<>();
//
//
//    public PendingGame startNewGame(TSUser user){
//        pending.add(user);
//        if(pending.size() >= 2 && currentlyPlaying.isEmpty()){
//            currentlyPlaying.clear();
//            pending.forEach( p -> currentlyPlaying.add(p));
//            pending.clear();
//
//            Random randomGenerator = new Random();
//            List<TestCultura> teste = testService.getAll();
//
//            TestCultura picked = teste.get(randomGenerator.nextInt(teste.size()));
//
//            PendingGame pendingGame = new PendingGame(currentlyPlaying, picked);
//            return pendingGame;
//        } else {
//            System.out.println("I still have to wait.");
//            return null;
//        }
//    }
//
//    public List<Game> gameOver(Game game){
//        this.currentlyPlaying.remove(game.getUser());
//        this.lastResults.add(game);
//        repository.save(game);
//        if(currentlyPlaying.size() == 0){
//            return lastResults;
//        }else{
//            return null;
//        }
//    }
//
//    public List<TSUser> getPending() {
//        return pending;
//    }
//
//    public PendingGame initGameForExistingUsers(){
//        if(pending.size() >= 2){
//            currentlyPlaying.clear();
//            pending.forEach( p -> currentlyPlaying.add(p));
//            pending.clear();
//
//            Random randomGenerator = new Random();
//            List<TestCultura> teste = testService.getAll();
//            TestCultura picked = teste.get(randomGenerator.nextInt(teste.size()));
//
//            PendingGame pendingGame = new PendingGame(currentlyPlaying, picked);
//            return pendingGame;
//        }else{
//            System.out.println("I still have to wait.");
//            return null;
//        }
//    }
//
//    public List<RestResponse> getRestResponse(TSUser user){
//        List<Game> allGames = repository.findAll();
//        List<Game> allGamesForMe = allGames.stream().filter(g -> g.getUser().getUsername().equals(user.getUsername())).collect(Collectors.toList());
//
//
//        List<RestResponse> all = new ArrayList<>();
//        for (Game crtGame : allGamesForMe){
//            List<Game> otherRelevantGames = allGames.stream().filter(g -> g.getTestCultura().getId() == crtGame.getTestCultura().getId()).collect(Collectors.toList());
//
//            List<RestResponse> otherResp = new ArrayList<>();
//            otherRelevantGames.forEach( other ->{
//                if(!other.getUser().getUsername().equals(crtGame.getUser().getUsername()))
//                    otherResp.add(new RestResponse(other.getUser(), other.getPunctaj(), null));
//            });
//
//            RestResponse restResponse = new RestResponse(crtGame.getUser(), crtGame.getPunctaj(), otherResp);
//            all.add(restResponse);
//        }
//
//        return all;
//    }
}
