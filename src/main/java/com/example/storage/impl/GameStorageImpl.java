
package com.example.storage.impl;

import com.example.dto.Game;
import com.example.dto.Opinion;
import com.example.dto.Platform;
import com.example.storage.GameStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GameStorageImpl implements GameStorage {

    private List<Game> gameList = new ArrayList<>();

    @Override
    public void addGame(Game game) {
        this.gameList.add(game);
    }

    @Override
    public void addRating(long id, Integer rating){
        for  (Game game :gameList){
            if (game.getGameId() == id){
                game.addRating(rating);
            }
        }
    }

    @Override
    public  void addOpinion(long gameID, Opinion opinion){
    }

    @Override
    public Game getGameData(long id){
        for (Game game :gameList){
            if (game.getGameId() == id){
                return game;
            }
        }
        return null;
    }

    public GameStorageImpl() {
        long id = System.currentTimeMillis();

        this.gameList.add(new Game(id+1, "Need For Speed:  High Stakes","Electronic Arts", LocalDate.of(2017,12,01), new Platform("XBOX")));
        this.gameList.add(new Game(id+2, "Red Dead Redemption 2","Rockstar Games", LocalDate.of(2019,11,5), new Platform("Play Station")));
        this.gameList.add(new Game(id+3, "Disco Elysium","Electronic Arts", LocalDate.of(2019,10,15), new Platform("PC")));
        this.gameList.add(new Game(id+4, "Rally Championship 2000","Magnetic Fields", LocalDate.of(2000,1,31), new Platform("PC")));
        this.gameList.add(new Game(id+5, "Half life 2","Valve Software", LocalDate.of(2004,11,16), new Platform("PC")));
        this.gameList.add(new Game(id+6, "Grand Theft Auto: Vice City","Rockstar North", LocalDate.of(2003,3,12), new Platform("Play Station")));
        this.gameList.add(new Game(id+7, "Worms","Team 17", LocalDate.of(1995,3,19), new Platform("DOS")));
        this.gameList.add(new Game(id+8, "The Sims","Maxis", LocalDate.of(2000,2,4), new Platform("PC")));
    }
}
