package com.snakegame.snakegame.service;

import org.springframework.stereotype.Service;

import com.snakegame.snakegame.model.Cell;
import com.snakegame.snakegame.model.SnakeGame;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Collection;

@Service
public class SnakeGameService {

    private int boardSize = 6;

    protected final int[][] board = new int[boardSize][boardSize];

    private static final int INITIAL_SNAKE_LENGTH = 3;

    private SnakeGame gameState = new SnakeGame(boardSize);

    public int getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;

    }

    public SnakeGame getGameStateForPlayer(String playerId) {
        // if there is no snake for that player then we add one.
        if (!gameState.getSnakes().containsKey(playerId))
            return initializeGame(playerId, gameState);
        return gameState;
    }

    public SnakeGame getGameState() {
        // if there is no snake for that player then we add one.

        return gameState;
    }

    private SnakeGame initializeGame(String playerId, SnakeGame gameState) {
        // Generate snake for that player.
        LinkedList<Cell> initialSnake = generateSnake(gameState.getSnakes().values(), gameState.getFruits());
        gameState.getSnakes().put(playerId, initialSnake);
        // If no fruits generate a fruit in the game.
        if (gameState.getFruits().isEmpty())
            generateNewFruit(gameState, board);

        return gameState;
    }

    private LinkedList<Cell> generateSnake(Collection<LinkedList<Cell>> existingSnakes, List<Cell> fruits) {
        LinkedList<Cell> snake = new LinkedList<>();
        Random random = new Random();

        // Generate a random initial position for the player's snake
        int initialX = random.nextInt(boardSize - 1);
        int initialY = random.nextInt(boardSize - 1);

        // Ensure that the initial position is not occupied by any existing snake
        while (isSnakeOccupyingPosition(initialX, initialY, existingSnakes, fruits)) {
            initialX = random.nextInt(boardSize - 1);
            initialY = random.nextInt(boardSize - 1);
        }

        // Generate the initial snake
        for (int i = 0; i < INITIAL_SNAKE_LENGTH; i++) {
            snake.add(new Cell(initialX, initialY + i));
        }

        return snake;
    }

    private boolean isSnakeOccupyingPosition(int x, int y, Collection<LinkedList<Cell>> existingSnakes,
            List<Cell> fruits) {
        for (LinkedList<Cell> existingSnake : existingSnakes) {
            if (existingSnake.stream().anyMatch(cell -> cell.getX() == x && cell.getY() == y)) {
                return true; // Position is occupied by an existing snake
            }
        }
        return fruits.contains(new Cell(x, y)); // Also check if its occupying a fruit.
    }

    public void handleUserInput(String playerId, int direction) {
        // creates a game state for the user if it doesn't already exist
        SnakeGame snakeGame = getGameStateForPlayer(playerId);
        if (!snakeGame.isGameOver()) {
            moveSnake(snakeGame, playerId, direction);
            checkCollision(snakeGame, playerId);
            checkFruit(snakeGame, board);
            updateBoard(snakeGame);
        }
    }

    private void moveSnake(SnakeGame snakeGame, String playerId, int direction) {

        LinkedList<Cell> snake = snakeGame.getSnakes().get(playerId);

        if (snake != null && !snake.isEmpty()) {
            // Move the snake based on the direction
            Cell head = snake.getFirst();

            // Calculate the new head position based on the current direction
            // x is vertical and y is horizontal
            Cell newHead;
            switch (direction) {
                case 0:
                    newHead = new Cell(head.getX() - 1, head.getY()); // UP
                    break;
                case 2:
                    newHead = new Cell(head.getX() + 1, head.getY()); // Down
                    break;
                case 3:
                    newHead = new Cell(head.getX(), head.getY() - 1); // Left
                    break;
                case 1:
                    newHead = new Cell(head.getX(), head.getY() + 1); // Right
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + direction); // maybe better message?
            }

            // Move the snake by adding the new head
            snake.addFirst(newHead);
            if (!snakeGame.isFruitEaten()) {
                snake.removeLast();
            } else {
                snakeGame.addScoreForPlayer(playerId);
            }

        }
    }

    private void checkCollision(SnakeGame snakeGame, String playerId) {
        LinkedList<Cell> snake = snakeGame.getSnakes().get(playerId);
        Cell head = snake.getFirst();

        // Check for collisions with the board boundaries
        if (head.getX() < 0 || head.getX() >= boardSize || head.getY() < 0 || head.getY() >= boardSize) {
            endGame(snakeGame, "Game Over for " + playerId + " - Snake collided with the board boundary!");
        }

        // Check for collisions with the snake's own body
        if (snake.size() > 1 && snake.subList(1, snake.size()).contains(head)) {
            endGame(snakeGame, "Game Over for " + playerId + " - Snake collided with its own body!");
        }

        // Check for collisions with other snakes
        for (Map.Entry<String, LinkedList<Cell>> entry : snakeGame.getSnakes().entrySet()) {
            String otherPlayerId = entry.getKey();
            if (!otherPlayerId.equals(playerId)) {
                LinkedList<Cell> otherSnake = entry.getValue();
                if (otherSnake.contains(head)) {
                    endGame(snakeGame, "Game Over for " + playerId + " - Snake collided with another snake!");
                }
            }
        }
    }

    private void endGame(SnakeGame snakeGame, String message) {
        snakeGame.setGameOver(true);
        snakeGame.setMessage(message);
        // Additional logic for ending the game
    }

    private boolean isCellOccupied(int x, int y, Collection<LinkedList<Cell>> snakeBodies) {
        // Check if the cell is occupied by any snake
        for (LinkedList<Cell> snakeBody : snakeBodies) {
            if (snakeBody.stream().anyMatch(cell -> cell.getX() == x && cell.getY() == y)) {
                return true; // Cell is occupied by a snake
            }
        }
        return false; // Cell is not occupied by any snake
    }

    private void updateBoard(SnakeGame snakeGame) {
        if (!snakeGame.isGameOver()) {
            // Initialize the board with zeros or empty cells

            // Update the board with snake positions
            updateSnakesOnBoard(snakeGame);

            // Update the board with apple positions
            updateFruitsOnBoard(snakeGame);

            // Set the updated board in the SnakeGame instance
            snakeGame.setBoard(board);
        }
    }

    private void updateFruitsOnBoard(SnakeGame snakeGame) {
        if (snakeGame.getFruits().isEmpty())
            generateNewFruit(snakeGame, board);

    }

    private void updateSnakesOnBoard(SnakeGame snakeGame) {
        for (Map.Entry<String, LinkedList<Cell>> entry : snakeGame.getSnakes().entrySet()) {
            LinkedList<Cell> snake = entry.getValue();
            for (Cell cell : snake) {
                // Update the board with the snake's body
                board[cell.getX()][cell.getY()] = 1;
            }
        }
    }

    private void checkFruit(SnakeGame snakeGame, int[][] board) {
        if (snakeGame.isFruitEaten()) {
            // Fruit has been eaten, generate a new one
            generateNewFruit(snakeGame, board);
        }
    }

    private void generateNewFruit(SnakeGame snakeGame, int[][] board) {

        Random random = new Random();

        int fruitX;
        int fruitY;

        do {
            // Generate random coordinates for the new fruit
            fruitX = random.nextInt(boardSize);
            fruitY = random.nextInt(boardSize);
        } while (isCellOccupied(fruitX, fruitY, snakeGame.getSnakes().values()));

        // Set the new fruit on the board
        snakeGame.getFruits().add(new Cell(fruitX, fruitY));
        // Update the board to represent the presence of a fruit (e.g., set to 2)
        board[fruitX][fruitY] = 2;
    }

}
