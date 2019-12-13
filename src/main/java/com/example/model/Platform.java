package com.example.model;

public class Platform {
    private String name;

    @Override
    public String toString() {
        return "Platform{" + "\n" +
                "   name='" + name + "'\n"+
                '}';
    }

    public Platform(){}

    public Platform(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
