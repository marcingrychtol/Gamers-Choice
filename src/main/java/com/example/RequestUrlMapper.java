package com.example;

import com.example.controller.GameController;
import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.Response;


import static fi.iki.elonen.NanoHTTPD.Method;
import static fi.iki.elonen.NanoHTTPD.newFixedLengthResponse;
import static fi.iki.elonen.NanoHTTPD.Response.Status.*;

public class RequestUrlMapper {

    private static final String POST_ADD_GAME = "/game/addGame";
    private static final String POST_ADD_RATING = "/game/addRating";
    private static final String POST_ADD_OPINION = "/game/addOpinion";
    private static final String GET_GAME_DATA = "/game/getGameData";

    private GameController gameController = new GameController();

    public Response delegateRequest(NanoHTTPD.IHTTPSession session) {

        Method HTTPmethod = session.getMethod();

        switch (HTTPmethod) {
            case POST:
                return delegatePOST(session);
            case GET:
                return delegateGET(session);
            default:
                return newFixedLengthResponse(NOT_FOUND, "text/plain", "Bad method: " + session.getMethod().toString());
        }
    }

    Response delegatePOST(NanoHTTPD.IHTTPSession session) {
        String uri = session.getUri();
        switch (uri) {
            case POST_ADD_GAME:
                return gameController.serveAddGame(session);
            case POST_ADD_OPINION:
                return gameController.serveAddOpinion(session);
            case POST_ADD_RATING:
                return gameController.serveAddRating(session);
            default:
                return newFixedLengthResponse(NOT_FOUND,"text/plain", "Bad POST request: " + session.getUri());
        }
    }

    Response delegateGET(NanoHTTPD.IHTTPSession session) {
        String uri = session.getUri();
        switch (uri) {
            case GET_GAME_DATA:
                return gameController.serveGetGameData(session);
            default:
                return newFixedLengthResponse(NOT_FOUND,"text/plain","Bad GET request: " + session.getUri());
        }
    }
}
