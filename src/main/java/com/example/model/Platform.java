package com.example.model;

public class Platform {
    private int id;
    private String secondName;
    private String name;

    @Override
    public String toString() {
        return "Platform{" + "\n" +
                "   name='" + name + "'\n"+
                "   second name='" + secondName + "'\n"+
                '}';
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public Platform(){}

    public Platform(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
