package com.example.storage;

import com.example.dto.Game;

import java.util.ArrayList;
import java.util.List;

public abstract class GameStorage implements GameStoraging{
    public List<Game> getGameList() {
        return gameList;
    }

    public void setGameList(List<Game> gameList) {
        this.gameList = gameList;
    }

    private List<Game> gameList = new ArrayList<>();

}
