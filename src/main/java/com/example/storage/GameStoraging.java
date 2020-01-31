package com.example.storage;

import com.example.model.Game;
import com.example.model.Opinion;

public interface GameStoraging {

    void addGame(Game game);

    void addRating(int id, Integer rating);

    void addOpinion(int id, Opinion opinion);

    Game getGameData(int id);
}
