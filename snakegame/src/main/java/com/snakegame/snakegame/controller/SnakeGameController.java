package com.snakegame.snakegame.controller;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.snakegame.snakegame.model.SnakeGame;
import com.snakegame.snakegame.service.SnakeGameService;

@RestController
@RequestMapping("/api/snake")
public class SnakeGameController {

    private final SnakeGameService snakeGameService;

    public SnakeGameController(SnakeGameService snakeGameService) {
        this.snakeGameService = snakeGameService;

    }

    @GetMapping("/gameStatus/{playerId}")
    public ResponseEntity<SnakeGame> snakeGame(@PathVariable String playerId) {
        SnakeGame updatedGameState = snakeGameService.getGameState(playerId);
        return ResponseEntity.ok(updatedGameState);
    }

    @PostMapping("/move/{playerId}/{direction}")
    public ResponseEntity<SnakeGame> moveSnake(@PathVariable String playerId, @PathVariable String direction) {
        snakeGameService.handleUserInput(playerId, direction);
        SnakeGame updatedGameState = snakeGameService.getGameState(playerId);

        // Create DTO for fruits, snakes = [player id etc]

        return ResponseEntity.ok(updatedGameState);
    }

}
