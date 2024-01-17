package com.snakegame.snakegame.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class SnakeGame {
    private Map<String, LinkedList<Cell>> snakes; // Map of player IDs to their respective snakes
    private Map<String, Cell> apples; // Map of player IDs to their respective apples
    private Direction defaultDirection; // Default direction for new players
    private char[][] board; // Representation of the game board
    private boolean isGameOver;
    private String message;

    // Constructors, getters, and setters...

    public SnakeGame(int boardSize) {
        this.snakes = new HashMap<>();
        this.apples = new HashMap<>();
        this.defaultDirection = Direction.UP;
        this.board = new char[boardSize][boardSize];
        this.isGameOver = false;
        this.message = "Game in progress";
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = ' '; // Initialize all cells as empty
            }
        }
    }

    public Map<String, LinkedList<Cell>> getSnakes() {
        return snakes;
    }

    public Map<String, Cell> getApples() {
        return apples;
    }

    public Direction getDefaultDirection() {
        return defaultDirection;
    }

    public void setDefaultDirection(Direction defaultDirection) {
        this.defaultDirection = defaultDirection;
    }

    public char[][] getBoard() {
        return board;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
