package com.snakegame.snakegame.model;

public class MoveRequest {
    private String playerId;
    private Integer direction;

    // Getters and setters

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }
}