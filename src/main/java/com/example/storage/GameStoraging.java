package com.example.storage;

import com.example.model.Game;
import com.example.model.Opinion;

public interface GameStoraging {

    void addGame(Game game);

    void addRating(long id, Integer rating);

    void addOpinion(long id, Opinion opinion);

    Game getGameData(long id);
}
