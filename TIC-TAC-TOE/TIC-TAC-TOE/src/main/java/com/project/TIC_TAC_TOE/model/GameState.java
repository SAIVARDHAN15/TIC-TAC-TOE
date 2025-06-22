// GameState.java
package com.project.TIC_TAC_TOE.model;

public class GameState {
    private char[][] board;
    private char currentPlayer;
    private GameStatus status;

    // Constructors
    public GameState() {}

    public GameState(char[][] board, char currentPlayer, GameStatus status) {
        this.board = board;
        this.currentPlayer = currentPlayer;
        this.status = status;
    }

    // Getters and Setters
    public char[][] getBoard() {
        return board;
    }

    public void setBoard(char[][] board) {
        this.board = board;
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(char currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }
}
