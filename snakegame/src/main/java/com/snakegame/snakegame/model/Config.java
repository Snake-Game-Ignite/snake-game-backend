package com.snakegame.snakegame.model;

public class Config {

    private int boardSize = 6;

    private int initialSnakeLength = 3;

    private boolean keepScore = true;

    // Getters and setters

    public int getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }
    public int getinitialSnakeLength() {
        return initialSnakeLength;
    }

    public void setinitialSnakeLength(int initialSnakeLength) {
        this.initialSnakeLength = initialSnakeLength;
    }

    public boolean getKeepScore() {
        return keepScore;
    }

    public void setKeepScore(boolean keepScore) {
        this.keepScore = keepScore;
    }

}
