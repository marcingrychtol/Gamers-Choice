package com.example.controller;

import com.example.model.Game;
import com.example.storage.GameStorage;
import com.example.storage.impl.GameStorageImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fi.iki.elonen.NanoHTTPD.IHTTPSession;
import fi.iki.elonen.NanoHTTPD.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static fi.iki.elonen.NanoHTTPD.Response.Status.*;
import static fi.iki.elonen.NanoHTTPD.newFixedLengthResponse;

public class GameController {

    private static final String GAME_ID_PARAMETER = "gameId";

    private GameStorage gameStorage = new GameStorageImpl();
    private ObjectMapper objectMapper = new ObjectMapper();

    private String objectBody;

    public Response serveAddGame(IHTTPSession session) {

        if(!mapToString(session)){
            return newFixedLengthResponse(INTERNAL_ERROR,"text/plain","Error while buffering data!");
        }

        try {
            Game game = objectMapper.readValue(objectBody, Game.class);
            game.setGameId(System.currentTimeMillis());
            gameStorage.addGame(game);
            return newFixedLengthResponse(OK,"text/plain","Game suscessfully added: " + gameStorage.getGameData(game.getGameId()).toString());
        } catch (JsonProcessingException e) {
            return newFixedLengthResponse(INTERNAL_ERROR,"text.plain","Unable to parse Game data! " + e.getMessage());
        }

    }

    public Response serveAddRating(IHTTPSession session) {
        return null;
    }

    public Response serveAddOpinion(IHTTPSession session) {
        return null;
    }

    public Response serveGetGameData(IHTTPSession session) {

        Map<String, List<String>> parameterMap = session.getParameters();
        List<String> parameterContent = new ArrayList<>();

        if (parameterMap.containsKey(GAME_ID_PARAMETER)){
            parameterContent = parameterMap.get(GAME_ID_PARAMETER);
            long gameId;
//            gameId = session.getHeaders().get

        }

        return null;
    }

    private boolean mapToString(IHTTPSession session){
        int buffLength = Integer.parseInt(session.getHeaders().get("content-length"));
        byte[] buffer = new byte[buffLength];
        try {
            session.getInputStream().read(buffer,0,buffLength);
        } catch (IOException e) {
            return false;
        }
        this.objectBody = new String(buffer).trim();

        return true;
    }

}
