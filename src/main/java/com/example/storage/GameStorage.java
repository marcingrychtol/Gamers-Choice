package com.example.storage;

import com.example.dto.Game;
import com.example.dto.Opinion;

public interface GameStorage {

    void addGame(Game game);

    void addRating(long id, Integer rating);

    void addOpinion(long id, Opinion opinion);

    Game getGameData(long id);
}
