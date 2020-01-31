package com.example.storage.impl;

import com.example.model.Game;
import com.example.model.Opinion;
import com.example.model.Platform;
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
        final String ADD_QUERY =
                "INSERT INTO ratings (id, game_id, rating) VALUES" +
                        "(?,?,?);";

        Connection connection = initializeConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(ADD_QUERY);
            preparedStatement.setInt(1,rating);
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
    public void addOpinion(long id, Opinion opinion) {
        final String ADD_QUERY =
                "INSERT INTO opinions (id, game_id, author, content) VALUES" +
                        "(?,?,?,?,?);";

        Connection connection = initializeConnection();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(ADD_QUERY);
            preparedStatement.setLong(1,id);
            preparedStatement.setInt(2, opinion.getGameId());
            preparedStatement.setString(3, opinion.getAuthorName());
            preparedStatement.setString(4, opinion.getOpinionContent());
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
    public Game getGameData(long id) {
        final String GET_GAME_BYID_QUERY = "SELECT * FROM games WHERE id=?";
        final String GET_OPINIONS_BYID_QUERY = "SELECT * FROM opinions WHERE game_id=?";
        final String GET_PLATFORM_BYID_QUERY = "SELECT * FROM platforms WHERE game_id=?";
        final String GET_RATINGS_BYID_QUERY = "SELECT * FROM ratings WHERE game_id=?";

        Connection connection = initializeConnection();
        PreparedStatement preparedStatement = null;

        Game game = new Game();

        try {
            preparedStatement = connection.prepareStatement(GET_GAME_BYID_QUERY);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                game.setGameId(resultSet.getLong("id"));
                game.setName(resultSet.getString("name"));
                game.setProducer(resultSet.getString("producer"));
                game.setPremiereDate(resultSet.getDate("year_of_published"));
            }

            preparedStatement = connection.prepareStatement(GET_OPINIONS_BYID_QUERY);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Opinion opinion = new Opinion();
                opinion.setAuthorName(resultSet.getString("author"));
                opinion.setOpinionContent(resultSet.getString("content"));
                game.getOpinions().add(opinion);
            }

            preparedStatement = connection.prepareStatement(GET_PLATFORM_BYID_QUERY);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Platform platform = new Platform();
                platform.setName(resultSet.getString("name"));
                platform.setName(resultSet.getString("second_name"));
                game.setPlatform(platform);
            }

            preparedStatement = connection.prepareStatement(GET_RATINGS_BYID_QUERY);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Integer rating = 0;
                rating = resultSet.getInt("rating");
                game.getRatings().add(rating);
            }

            return game;

        } catch (SQLException e) {
            System.err.println("Failed to invoke select by id query: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to invoke select by id query: " + e.getMessage());
        } finally {
            closeConnection(connection, preparedStatement);
        }

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
