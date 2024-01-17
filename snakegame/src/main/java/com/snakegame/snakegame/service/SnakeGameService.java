
package com.snakegame.snakegame.service;

import org.springframework.stereotype.Service;

import com.snakegame.snakegame.model.Cell;
import com.snakegame.snakegame.model.SnakeGame;

import com.snakegame.snakegame.model.Direction;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@Service
public class SnakeGameService {
    private static final int BOARD_SIZE = 10; // Adjust the board size as needed

    private Map<String, SnakeGame> playerGames = new HashMap<>();

    public SnakeGame getGameState(String playerId) {
        return playerGames.computeIfAbsent(playerId, k -> new SnakeGame(BOARD_SIZE));
    }

    public void handleUserInput(String playerId, String direction) {
        SnakeGame snakeGame = getGameState(playerId);

        if (snakeGame.isGameOver()) {
            initializeGame(playerId);
        } else {
            moveSnake(snakeGame, playerId, direction);
            checkCollision(snakeGame, playerId);
            checkApple(snakeGame, playerId);
        }
    }

    public void moveSnake(SnakeGame snakeGame, String playerId, String direction) {
        if (!snakeGame.isGameOver()) {
            LinkedList<Cell> snake = snakeGame.getSnakes().get(playerId);

            // Get the head of the snake
            Cell head = snake.getFirst();

            // Calculate the new head position based on the current direction
            Cell newHead;
            switch (Direction.valueOf(direction.toUpperCase())) {
                case UP:
                    newHead = new Cell(head.getX() - 1, head.getY());
                    break;
                case DOWN:
                    newHead = new Cell(head.getX() + 1, head.getY());
                    break;
                case LEFT:
                    newHead = new Cell(head.getX(), head.getY() - 1);
                    break;
                case RIGHT:
                    newHead = new Cell(head.getX(), head.getY() + 1);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + direction);
            }

            // ... (Check for collisions and move logic similar to previous examples)

            // Move the snake by adding the new head
            snake.addFirst(newHead);

            // Check if the snake has eaten the apple
            if (newHead.equals(snakeGame.getApples().get(playerId))) {
                snakeGame.setAppleEaten(true);
                generateNewApple(snakeGame, playerId);
            } else {
                // If the snake hasn't eaten the apple, remove the tail (last cell)
                snake.removeLast();
            }

            // Update the board with the new snake position
            updateBoard(snakeGame);
        }
    }

    private void initializeGame(String playerId) {
        playerGames.put(playerId, new SnakeGame(BOARD_SIZE));
    }

    private void checkCollision(SnakeGame snakeGame, String playerId) {
        LinkedList<Cell> snake = snakeGame.getSnakes().get(playerId);
        Cell head = snake.getFirst();

        // Check for collisions with the board boundaries
        if (head.getX() < 0 || head.getX() >= BOARD_SIZE ||
                head.getY() < 0 || head.getY() >= BOARD_SIZE) {
            endGame(snakeGame, playerId, "Game Over - Snake collided with the board boundary!");
        }

        // Check for collisions with the snake's own body
        if (snake.size() > 1 && snake.subList(1, snake.size()).contains(head)) {
            endGame(snakeGame, playerId, "Game Over - Snake collided with its own body!");
        }
    }

    private void checkApple(SnakeGame snakeGame, String playerId) {
        if (snakeGame.isAppleEaten()) {
            // Apple has been eaten, generate a new one
            generateNewApple(snakeGame, playerId);
        }
    }

    private void updateBoard(SnakeGame snakeGame) {
        // Logic to update the char[][] board based on the current state of the snake
        // and apple
        // ... (implementation based on your requirements)
    }

    private void generateNewApple(SnakeGame snakeGame, String playerId) {
        // Logic to generate a new position for the apple
        // ... (implementation based on your requirements)
    }

    private void endGame(SnakeGame snakeGame, String playerId, String message) {
        snakeGame.setGameOver(true);
        snakeGame.setMessage(message);
        // Additional logic for ending the game
    }
}