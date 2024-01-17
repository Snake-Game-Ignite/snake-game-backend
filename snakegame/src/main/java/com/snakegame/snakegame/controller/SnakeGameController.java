package com.snakegame.snakegame.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.snakegame.snakegame.model.SnakeGame;
import com.snakegame.snakegame.service.SnakeGameService;

@Controller
public class SnakeGameController {

    private final SnakeGameService snakeGameService;

    public SnakeGameController(SnakeGameService snakeGameService) {
        this.snakeGameService = snakeGameService;
    }

    @GetMapping("/snake")
    public String snakeGame(
            Model model,
            @RequestParam(name = "playerId", required = true) String playerId) {
        if (playerId == null || playerId.isEmpty()) {
            playerId = "player_" + System.currentTimeMillis();
        }

        SnakeGame snakeGame = snakeGameService.getGameState(playerId);
        model.addAttribute("snakeGame", snakeGame);
        model.addAttribute("playerId", playerId);

        return "snakeGame";
    }

    @PostMapping("/snake/move")
    public String moveSnake(
            @RequestParam String direction,
            @RequestParam String playerId,
            Model model) {
        SnakeGame snakeGame = snakeGameService.getGameState(playerId);
        snakeGameService.moveSnake(snakeGame, direction);
        model.addAttribute("playerId", playerId);

        return "redirect:/snake?playerId=" + playerId;
    }
}
