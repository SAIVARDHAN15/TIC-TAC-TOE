package com.project.TIC_TAC_TOE.controller;

import com.project.TIC_TAC_TOE.model.GameRequest;
import com.project.TIC_TAC_TOE.model.GameState;
import com.project.TIC_TAC_TOE.model.MoveRequest;
import com.project.TIC_TAC_TOE.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game")
@CrossOrigin(origins = "http://localhost:8080")
public class GameController {
    @Autowired
    private GameService gameService;

    @PostMapping("/start")
    public ResponseEntity<Void> startGame(@RequestBody GameRequest gameRequest) {
        gameService.initializeGame(gameRequest.getBoardSize(), gameRequest.isVsComputer());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/move")
    public ResponseEntity<GameState> makeMove(@RequestBody MoveRequest moveRequest) {
        GameState gameState = gameService.makeMove(moveRequest);
        return ResponseEntity.ok(gameState);
    }

    @GetMapping("/state")
    public ResponseEntity<GameState> getGameState() {
        return ResponseEntity.ok(gameService.getGameState());
    }

    @GetMapping("/current-player")
    public ResponseEntity<String> getCurrentPlayer() {
        return ResponseEntity.ok(String.valueOf(gameService.getCurrentPlayer()));
    }

    @GetMapping("/computer-move")
    public ResponseEntity<int[]> getComputerMove() {
        return ResponseEntity.ok(gameService.getComputerMove());
    }

}
