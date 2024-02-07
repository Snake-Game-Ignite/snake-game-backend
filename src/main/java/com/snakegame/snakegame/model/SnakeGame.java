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
    private Map<String, Integer> score = new HashMap<>();
    private Map<String, Integer> deaths = new HashMap<>();

    public SnakeGame(int boardSize, Map<String, Integer> score, Map<String, Integer> deaths) {
        this.snakes = new HashMap<>();
        this.fruits = new ArrayList<>();
        this.gameOver = false;
        this.message = "";
        this.isFruitEaten = false;
        this.board = new int[boardSize][boardSize];
        this.score = score;
        this.deaths = deaths;
    }

    public int[][] getBoard() {
        return board;
    }

    public int getPlayerScore(String playerId) {
        return score.get(playerId);
    }

    public void addScoreForPlayer(String player) {

        if (score.containsKey(player)) {
            int playerCurrentScore = score.get(player);
            int newScore = playerCurrentScore + 1;
            score.put(player, newScore);
        } else {
            score.put(player, 1);
        }
    }

    public int getPlayerDeaths(String playerId) {
        return deaths.get(playerId);
    }

    public void addDeathForPlayer(String player) {
        if (deaths.containsKey(player)) {
            int playerCurrentDeaths = deaths.get(player);
            int newDeaths = playerCurrentDeaths + 1;
            deaths.put(player, newDeaths);
        } else {
            deaths.put(player, 1);
        }
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

    public Map<String, Integer> getScore() {
        return score;
    }

    public Map<String, Integer> getDeaths() {
        return deaths;
    }

}