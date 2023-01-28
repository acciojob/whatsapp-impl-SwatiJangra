package com.driver;

public class Group {
    private String name;
    private int numberOfParticipants;

    public Group() { }
    public Group(String name, int numberOfParticipants) {
        this.name=name;
        this.numberOfParticipants=numberOfParticipants;
    }

    public String getName(String name) {
        return name;
    }
    public void setName(String name) {
        this.name=name;
    }

    public int getNumberOfParticipants(int numberOfParticipants) {
        return numberOfParticipants;
    }
    public void setNumberOfParticipants(int numberOfParticipants) {
        this.numberOfParticipants=numberOfParticipants;
    }

}
