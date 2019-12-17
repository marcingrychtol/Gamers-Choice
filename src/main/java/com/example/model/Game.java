package com.example.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game {
    private long gameId;
    private String name;
    private String producer;
    private Platform platform;
    //    @JsonFormat (shape = Shape.STRING, pattern = "dd/MM/yyyy")
    //    private Date premiereDate;
    private List<Integer> ratings = new ArrayList<>();
    private List<Opinion> opinions = new ArrayList<>();

    public String getRatings() {
        return Arrays.toString(new List[]{ratings});
    }

    @Override
    public String toString() {
        return "Game{" + "\n" +
                " gameId= " + gameId + "\n" +
                " name= '" + name + "'\n" +
                " producer= '" + producer + "'\n" +
                " platform= '" + platform.toString() + "'\n" +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Game)) return false;

        Game game = (Game) o;

        if (gameId != game.gameId) return false;
        if (name != null ? !name.equals(game.name) : game.name != null) return false;
        if (producer != null ? !producer.equals(game.producer) : game.producer != null) return false;
//        if (premiereDate != null ? !premiereDate.equals(game.premiereDate) : game.premiereDate != null) return false;
        if (platform != null ? !platform.equals(game.platform) : game.platform != null) return false;
        if (ratings != null ? !ratings.equals(game.ratings) : game.ratings != null) return false;
        return opinions != null ? opinions.equals(game.opinions) : game.opinions == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (gameId ^ (gameId >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (producer != null ? producer.hashCode() : 0);
//        result = 31 * result + (premiereDate != null ? premiereDate.hashCode() : 0);
        result = 31 * result + (platform != null ? platform.hashCode() : 0);
        result = 31 * result + (ratings != null ? ratings.hashCode() : 0);
        result = 31 * result + (opinions != null ? opinions.hashCode() : 0);
        return result;
    }

    public Game(){
    }

    public Game(/*long gameId, */String name, String producer, Platform platform) {
//        this.gameId = gameId;
        this.name = name;
        this.producer = producer;
//        this.premiereDate = premiereDate;
        this.platform = platform;
    }
    public Game(long gameId, String name, String producer, Platform platform) {
        this.gameId = gameId;
        this.name = name;
        this.producer = producer;
//        this.premiereDate = premiereDate;
        this.platform = platform;
    }

    public void addRating(Integer rating){
        this.ratings.add(rating);
    }
    public void addOpinion(Opinion opinion){
        this.opinions.add(opinion);
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }
//
//    public Date getPremiereDate() {
//        return premiereDate;
//    }
//
//    public void setPremiereDate(Date premiereDate) {
//        this.premiereDate = premiereDate;
//    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }
}
