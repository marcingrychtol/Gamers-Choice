package com.example.storage.impl;

import com.example.model.Game;
import com.example.model.Opinion;
import com.example.model.Platform;
import com.example.model.Rating;
import com.example.storage.GameStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameStorageImpl implements GameStorage {

    private List<Game> gameList = new ArrayList<>();

    public GameStorageImpl() {
        this.gameList.add(new Game(1, "Need For Speed:  High Stakes","Electronic Arts", LocalDate.of(2017,12,01), new Platform("XBOX")));
        this.gameList.add(new Game(2, "Red Dead Redemption 2","Rockstar Games", LocalDate.of(2019,11,5), new Platform("Play Station")));
        this.gameList.add(new Game(3, "Disco Elysium","Electronic Arts", LocalDate.of(2019,10,15), new Platform("PC")));
        this.gameList.add(new Game(4, "Rally Championship 2000","Magnetic Fields", LocalDate.of(2000,1,31), new Platform("PC")));
        this.gameList.add(new Game(5, "Half life 2","Valve Software", LocalDate.of(2004,11,16), new Platform("PC")));
        this.gameList.add(new Game(6, "Grand Theft Auto: Vice City","Rockstar North", LocalDate.of(2003,3,12), new Platform("Play Station")));
        this.gameList.add(new Game(7, "Worms","Team 17", LocalDate.of(1995,3,19), new Platform("DOS")));
        this.gameList.add(new Game(8, "The Sims","Maxis", LocalDate.of(2000,2,4), new Platform("PC")));
    }


    @Override
    public void addGame(Game game) {
        this.gameList.add(game);
    }

    @Override
    public void addRating(long id, Integer rating){
        this.gameList.get(id);
    }

    public Map<Long, Opinion> getOpinionList() {
        return opinionList;
    }

    @Override
    public  void addOpinion(Integer gameID,Opinion opinion){
        this.opinionList.put(gameID,opinion);
    }

    @Override
    public Game getGameData(long id){
        return Game
    };


}
