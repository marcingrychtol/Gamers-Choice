package com.example.storage.impl;

import com.example.dto.Game;
import com.example.dto.Opinion;
import com.example.storage.GameStorage;

import java.sql.*;

public class PostgresGameStorage extends GameStorage {


    private static final String DB_ADDRESS = "jdbc:postgresql://localhost:5432/video_store";
    private static final String USER_PASS = "password";
    private static final String USER_NAME = "postgres";

    @Override
    public void addGame(Game game) {

        final String ADD_QUERY =
                "INSERT INTO games (id, producer, name, platform, premiere_date) VALUES" +
                        "(?,?,?,?,?);";

        Connection connection = initializeConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(ADD_QUERY);

            preparedStatement.setDouble(1,game.getGameId());
            preparedStatement.setString(2,game.getProducer());
            preparedStatement.setString(3,game.getName());
            preparedStatement.setString(4,game.getPlatform().getName());
            preparedStatement.setDate(5, Date.valueOf(game.getPremiereDate()));

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Failed to execute set or execute query.");
            e.printStackTrace();
            throw new RuntimeException("Failed to execute set or execute query: " + e.getMessage());
        } finally {
            closeConnection(connection, preparedStatement);
        }

    }

    @Override
    public void addRating(long id, Integer rating) {

    }

    @Override
    public void addOpinion(long id, Opinion opinion) {

    }

    @Override
    public Game getGameData(long id) {
        return null;
    }

    private Connection initializeConnection() {
        try {
            return DriverManager.getConnection(DB_ADDRESS, USER_NAME, USER_PASS);
        } catch (SQLException e) {
            System.err.println("Failed to initialize connection: " + e.getMessage());
            throw new RuntimeException("Failed to initialize connection: " + e.getMessage());
        }
    }

    private boolean closeConnection(Connection connection, Statement statement) {
        try {
            if (connection != null) {
                connection.close();
            }
            if (statement != null) {
                statement.close();
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            e.getMessage();
            throw new RuntimeException("Error during closing database connection!");
        }

    }
}
