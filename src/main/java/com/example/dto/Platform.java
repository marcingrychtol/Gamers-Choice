package com.example.dto;

public class Platform {
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

    public void setName(String name) {
        this.name = name;
    }
}
