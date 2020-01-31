package com.example.controller;

import com.example.di.DependencyController;
import com.example.model.Game;
import com.example.model.Opinion;
import com.example.storage.GameStorage;
import com.example.storage.GameStoraging;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
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
    private static final String GAME_RATING_PARAMETER = "rating";

    private GameStoraging gameStorage = DependencyController.getDependencyContainer().get(GameStorage.class);
    private ObjectMapper objectMapper = new ObjectMapper();

//    {
//        objectMapper.registerModule(new JavaTimeModule());
//        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
//    }

    private String incomeMappedObjectBody; // stores value from mapToString method
    private int incomeGameID; // stores value from checkGameExists method

    public Response serveAddGame(IHTTPSession session) {

        if (!mapToString(session)) {
            return newFixedLengthResponse(INTERNAL_ERROR, "text/plain", "Error while buffering data!");
        }

        try {
            Game game = objectMapper.readValue(incomeMappedObjectBody, Game.class);
            game.setGameId((int)System.currentTimeMillis());
            gameStorage.addGame(game);
            return newFixedLengthResponse(OK, "text/plain", "Game suscessfully added: " +
                    game.toString());
        } catch (IOException e) {
            return newFixedLengthResponse(INTERNAL_ERROR, "text.plain",
                    "Unable to parse Game data! " + e.getMessage());
        }

    }

    public Response serveAddRating(IHTTPSession session) {

        Map<String, List<String>> parameterMap = session.getParameters();
        List<String> parameterContentList = new ArrayList<>();

        if (!parameterMap.containsKey(GAME_ID_PARAMETER)) {
            return newFixedLengthResponse(BAD_REQUEST, "text/plain", "Wrong parameter: " + parameterMap);
        }

        Game game;
        parameterContentList = parameterMap.get(GAME_ID_PARAMETER);
        int gameId;

        try {
            gameId = Integer.parseInt(parameterContentList.get(0));
            game = gameStorage.getGameData(gameId);
            if (game == null) {
                return newFixedLengthResponse(NOT_FOUND, "text/plain",
                        "No such game in repository: " + gameId);
            }
        } catch (NumberFormatException e) {
            return newFixedLengthResponse(INTERNAL_ERROR, "text/plain",
                    "Internal error while parsing ID value: " + e.getMessage());
        }

        if (!parameterMap.containsKey(GAME_RATING_PARAMETER)) {
            return newFixedLengthResponse(BAD_REQUEST, "text/plain", "Wrong parameter: " + parameterMap);
        }

        int rating;
        parameterContentList = parameterMap.get(GAME_RATING_PARAMETER);

        try {
            rating = Integer.parseInt(parameterContentList.get(0));
            if (rating > 10 || rating < 0) {
                return newFixedLengthResponse(BAD_REQUEST, "text/plain",
                        "Rating out of range 0 .. 10: " + rating);
            }
            gameStorage.addRating(gameId,rating);
        } catch (NumberFormatException e) {
            return newFixedLengthResponse(INTERNAL_ERROR, "text/plain",
                    "Internal error while parsing ID value: " +
                    e.getMessage());
        }

        return newFixedLengthResponse(OK, "text/plain", "Rating added: " +
                gameStorage.getGameData(gameId).getName() +
                " " + gameStorage.getGameData(gameId).getRatings());

    }

    public Response serveAddOpinion(IHTTPSession session) {

        Response response = checkGameExists(session);

        if (response != null){
            return response;
        }

        if (!mapToString(session)){
            return newFixedLengthResponse(INTERNAL_ERROR, "text/plain", "Error while buffering data!");
        }

        try {
            Opinion opinion = objectMapper.readValue(incomeMappedObjectBody, Opinion.class );
            gameStorage.addOpinion((int)System.currentTimeMillis(),opinion);
        } catch (IOException e){
            return newFixedLengthResponse(INTERNAL_ERROR, "text.plain",
                    "Unable to parse Game data! " + e.getMessage());
        }

        return newFixedLengthResponse(OK,"text/plain","Game opinion added: " +
                gameStorage.getGameData(incomeGameID).getName());
    }

    public Response serveGetGameData(IHTTPSession session) {

        Map<String, List<String>> parameterMap = session.getParameters();
        List<String> parameterContentList;

        if (!parameterMap.containsKey(GAME_ID_PARAMETER)) {
            return newFixedLengthResponse(BAD_REQUEST, "text/plain", "Wrong parameter: " + parameterMap);
        }

        parameterContentList = parameterMap.get(GAME_ID_PARAMETER);

        try {
            int gameId = Integer.parseInt(parameterContentList.get(0));
            Game game = gameStorage.getGameData(gameId);

            if (game == null) {
                return newFixedLengthResponse(NOT_FOUND, "text/plain", "No such game in repository: " + gameId);
            }

            return newFixedLengthResponse(OK, "application/json", objectMapper.writeValueAsString(game));

        } catch (NumberFormatException e) {
            return newFixedLengthResponse(INTERNAL_ERROR, "text/plain", "Internal error while parsing ID value: " + e.getMessage());
        } catch (JsonProcessingException e) {
            return newFixedLengthResponse(INTERNAL_ERROR, "text/plain", "Internal error while creating JSON: " + e.getMessage());
        }

    }

    private boolean mapToString(IHTTPSession session) {
        int buffLength = Integer.parseInt(session.getHeaders().get("content-length"));
        byte[] buffer = new byte[buffLength];
        try {
            session.getInputStream().read(buffer, 0, buffLength);
        } catch (IOException e) {
            return false;
        }
        this.incomeMappedObjectBody = new String(buffer).trim();

        return true;
    }

    private Response checkGameExists(IHTTPSession session){
        Map<String, List<String>> parameterMap = session.getParameters();
        List<String> parameterContentList;

        if (!parameterMap.containsKey(GAME_ID_PARAMETER)) {
            return newFixedLengthResponse(BAD_REQUEST, "text/plain", "Wrong parameter: " + parameterMap);
        }

        parameterContentList = parameterMap.get(GAME_ID_PARAMETER);
        int gameId;

        try {
            gameId = Integer.parseInt(parameterContentList.get(0));
            if (gameStorage.getGameData(gameId) == null) {
                return newFixedLengthResponse(NOT_FOUND, "text/plain",
                        "No such game in repository: " + gameId);
            }
        } catch (NumberFormatException e) {
            return newFixedLengthResponse(INTERNAL_ERROR, "text/plain",
                    "Internal error while parsing ID value: " + e.getMessage());
        }
        this.incomeGameID = gameId;
        return null;
    }

}
