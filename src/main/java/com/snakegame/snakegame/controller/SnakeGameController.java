package com.snakegame.snakegame.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.snakegame.snakegame.model.Config;
import com.snakegame.snakegame.model.MoveRequest;
import com.snakegame.snakegame.model.SnakeGame;
import com.snakegame.snakegame.service.SnakeGameService;

@RestController
@RequestMapping("/api/snake")
public class SnakeGameController {

    private SnakeGameService snakeGameService;

    public SnakeGameController(SnakeGameService snakeGameService) {
        this.snakeGameService = snakeGameService;

    }

    @GetMapping("/state")
    public ResponseEntity<SnakeGame> snakeGame() {
        SnakeGame updatedGameState = snakeGameService.getGameState();
        return ResponseEntity.ok(updatedGameState);
    }

    @GetMapping("/restart")
    public ResponseEntity<SnakeGame> restart() {
        SnakeGame updatedGameState = snakeGameService.resetGameState(new Config());
        return ResponseEntity.ok(updatedGameState);
    }

    @PostMapping("/reset")
    public ResponseEntity<SnakeGame> reset(@RequestBody Config config) {
        SnakeGame updatedGameState = snakeGameService.resetGameState(config);
        return ResponseEntity.ok(updatedGameState);
    }

    @PostMapping("/move")
    public ResponseEntity<SnakeGame> moveSnake(@RequestBody MoveRequest moveRequest) {
        String playerId = moveRequest.getPlayerId();
        int direction = moveRequest.getDirection();
        snakeGameService.handleUserInput(playerId, direction);
        SnakeGame updatedGameState = snakeGameService.getGameStateForPlayer(playerId);

        return new ResponseEntity<>(updatedGameState, HttpStatus.OK);
    }

}
