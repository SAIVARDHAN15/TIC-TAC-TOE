package com.project.TIC_TAC_TOE.service;

import com.project.TIC_TAC_TOE.model.GameState;
import com.project.TIC_TAC_TOE.model.GameStatus;
import com.project.TIC_TAC_TOE.model.MoveRequest;
import com.project.TIC_TAC_TOE.util.AIUtils;
import org.springframework.stereotype.Service;

@Service
public class GameService {
    private char[][] board;
    private int boardSize;
    public boolean vsComputer;
    private char currentPlayer = 'X';
    private GameState gameState = new GameState();

    public void initializeGame(int boardSize, boolean vsComputer) {
        this.boardSize = boardSize;
        this.vsComputer = vsComputer;
        this.board = new char[boardSize][boardSize];
        this.currentPlayer = 'X';

        // Initialize empty board
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = ' ';
            }
        }

        // Create fresh game state
        this.gameState = new GameState();
        this.gameState.setBoard(board);
        this.gameState.setCurrentPlayer(currentPlayer);
        this.gameState.setStatus(GameStatus.IN_PROGRESS);
    }

    public GameState makeMove(MoveRequest moveRequest) {
        if (gameState.getStatus() != GameStatus.IN_PROGRESS) {
            return gameState;
        }

        int row = moveRequest.getRow();
        int col = moveRequest.getCol();

        if (row < 0 || row >= boardSize || col < 0 || col >= boardSize || board[row][col] != ' ') {
            return gameState;
        }

        board[row][col] = moveRequest.getPlayer();

        checkGameState();

        if (gameState.getStatus() == GameStatus.IN_PROGRESS) {
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        }

        gameState.setBoard(board);
        gameState.setCurrentPlayer(currentPlayer);
        return gameState;
    }

    private void checkGameState() {
        if (checkWin('X')) {
            gameState.setStatus(GameStatus.PLAYER_X_WON);
        } else if (checkWin('O')) {
            gameState.setStatus(GameStatus.PLAYER_O_WON);
        } else if (isBoardFull()) {
            gameState.setStatus(GameStatus.DRAW);
        } else {
            gameState.setStatus(GameStatus.IN_PROGRESS);
        }
    }

    private boolean checkWin(char player) {
        for (int i = 0; i < boardSize; i++) {
            boolean rowWin = true, colWin = true;
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j] != player) rowWin = false;
                if (board[j][i] != player) colWin = false;
            }
            if (rowWin || colWin) return true;
        }

        boolean diag1Win = true, diag2Win = true;
        for (int i = 0; i < boardSize; i++) {
            if (board[i][i] != player) diag1Win = false;
            if (board[i][boardSize - 1 - i] != player) diag2Win = false;
        }

        return diag1Win || diag2Win;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    public char[][] getBoard() {
        return board;
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public GameState getGameState() {
        return gameState;
    }

    public int[] getComputerMove() {
        if(boardSize > 3) return AIUtils.getRandomMove(board, boardSize);
        return AIUtils.getBestMove(board, boardSize);
    }
}
