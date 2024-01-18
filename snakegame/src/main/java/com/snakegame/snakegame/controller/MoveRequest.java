package com.snakegame.snakegame.controller;

public class MoveRequest {
    private String playerId;
    private int direction;

    // Getters and setters

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }
}