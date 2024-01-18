package com.snakegame.snakegame.model;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SnakeGame {
    private boolean gameOver;
    private String message;
    private Map<String, LinkedList<Cell>> snakes;
    private ArrayList<Cell> fruits;
    private boolean isFruitEaten;
    private int[][] board;

    public SnakeGame(int boardSize) {
        this.snakes = new HashMap<>();
        this.fruits = new ArrayList<>();
        this.gameOver = false;
        this.message = "";
        this.isFruitEaten = false;
        this.board = new int[boardSize][boardSize];
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, LinkedList<Cell>> getSnakes() {
        return snakes;
    }

    public List<Cell> getFruits() {
        return fruits;
    }

    public boolean isFruitEaten() {
        return isFruitEaten;
    }

    public void setFruitEaten(boolean fruitEaten) {
        isFruitEaten = fruitEaten;
    }

}