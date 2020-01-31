package com.example.storage.impl;

import com.example.model.Game;
import com.example.model.Opinion;
import com.example.model.Platform;
import com.example.storage.GameStorage;

import java.sql.*;
import java.sql.Time;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class PostgresGameStorage extends GameStorage {


    private static final String DB_ADDRESS = "jdbc:postgresql://localhost:5432/video_store";
    private static final String USER_PASS = "postgres";
    private static final String USER_NAME = "postgres";

    @Override
    public void addGame(Game game) {

        final String ADD_QUERY =
                "INSERT INTO games (producer, name, platform_id/*, premiere_date*/) VALUES" +
                        "(?,?,?);";

        Connection connection = initializeConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(ADD_QUERY);

            preparedStatement.setString(1,game.getProducer());
            preparedStatement.setString(2,game.getName());
            preparedStatement.setInt(3,game.getPlatform().getId());
//            preparedStatement.setObject(4, game.getPremiereDate());

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
    public void addRating(int id, Integer rating) {
        final String ADD_QUERY =
                "INSERT INTO ratings (game_id, rating) VALUES" +
                        "(?,?);";

        Connection connection = initializeConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(ADD_QUERY);
            preparedStatement.setInt(1, id);
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
    public void addOpinion(int id, Opinion opinion) {
        final String ADD_QUERY =
                "INSERT INTO opinions (game_id, author, content) VALUES" +
                        "(?,?,?);";

        Connection connection = initializeConnection();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(ADD_QUERY);
            preparedStatement.setInt(1, opinion.getGameId());
            preparedStatement.setString(2, opinion.getAuthorName());
            preparedStatement.setString(3, opinion.getOpinionContent());
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
    public Game getGameData(int id) {
        final String GET_GAME_BYID_QUERY = "SELECT * FROM games WHERE id=?";
        final String GET_OPINIONS_BYID_QUERY = "SELECT * FROM opinions WHERE game_id=?";
        final String GET_PLATFORM_BYID_QUERY = "SELECT * FROM platforms WHERE id=?";
        final String GET_RATINGS_BYID_QUERY = "SELECT * FROM ratings WHERE game_id=?";

        Connection connection = initializeConnection();
        PreparedStatement preparedStatement = null;

        Game game = new Game();

        try {
            preparedStatement = connection.prepareStatement(GET_GAME_BYID_QUERY);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            int platformId = 0;

            if (resultSet.next()) {

                game.setGameId(resultSet.getInt("id"));
                game.setName(resultSet.getString("name"));
                game.setProducer(resultSet.getString("producer"));
//                game.setPremiereDate(resultSet.getDate("premiere_date"));
                platformId = resultSet.getInt("platform_id");
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
            preparedStatement.setLong(1, platformId);
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

    public static String getDbAddress() {
        return DB_ADDRESS;
    }

    public static String getUserPass() {
        return USER_PASS;
    }

    public static String getUserName() {
        return USER_NAME;
    }

    public void setGameList(List<Game> gameList) {
        super.setGameList(gameList);
    }

    public List<Game> getGameList() {
        return super.getGameList();
    }

    public PostgresGameStorage() {
//        this.addGame(new Game("Need For Speed:  High Stakes","Electronic Arts", LocalDate.of(2017,12,01), new Platform("XBOX")));
//        this.addGame(new Game("Red Dead Redemption 2","Rockstar Games", LocalDate.of(2019,11,5), new Platform("Play Station")));
//        this.addGame(new Game("Disco Elysium","Electronic Arts", LocalDate.of(2019,10,15), new Platform("PC")));
//        this.addGame(new Game("Rally Championship 2000","Magnetic Fields", LocalDate.of(2000,1,31), new Platform("PC")));
//        this.addGame(new Game("Half life 2","Valve Software", LocalDate.of(2004,11,16), new Platform("PC")));
//        this.addGame(new Game("Grand Theft Auto: Vice City","Rockstar North", LocalDate.of(2003,3,12), new Platform("Play Station")));
//        this.addGame(new Game("Worms","Team 17", LocalDate.of(1995,3,19), new Platform("DOS")));
//        this.addGame(new Game("The Sims","Maxis", LocalDate.of(2000,2,4), new Platform("PC")));
    }
}
