package com.example.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Game {
    private long id;
    private String name;
    private String producer;
    private LocalDate premiereDate;
    private Platform platform;
    private List<Integer> ratings = new ArrayList<>();
    private List<Opinion> opinions = new ArrayList<>();


    public Game(long id, String name, String producer, LocalDate premiereDate, Platform platform) {
        this.id = id;
        this.name = name;
        this.producer = producer;
        this.premiereDate = premiereDate;
        this.platform = platform;
    }

    public void addRating(Integer rating){
        this.ratings.add(rating);
    }
    public void addOpinion(Opinion opinion){
        this.opinions.add(opinion);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public LocalDate getPremiereDate() {
        return premiereDate;
    }

    public void setPremiereDate(LocalDate premiereDate) {
        this.premiereDate = premiereDate;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }
}
