package com.project.TIC_TAC_TOE.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AIUtils {
    public static int[] getRandomMove(char[][] board, int boardSize) {
        List<int[]> emptyCells = new ArrayList<>();

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j] == ' ') {
                    emptyCells.add(new int[]{i, j});
                }
            }
        }

        if (emptyCells.isEmpty()) {
            return new int[]{-1, -1};
        }

        Random random = new Random();
        return emptyCells.get(random.nextInt(emptyCells.size()));
    }

    public static int[] getBestMove(char[][] board, int boardSize) {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = {-1, -1};

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = 'O'; // Computer move
                    int score = minimax(board, boardSize, 0, false);
                    board[i][j] = ' ';

                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = new int[]{i, j};
                    }
                }
            }
        }

        return bestMove;
    }

    private static int minimax(char[][] board, int boardSize, int depth, boolean isMaximizing) {
        Character winner = checkWinner(board, boardSize);
        if (winner != null) {
            if (winner == 'O') return 10 - depth;    // Prefer quicker wins
            else if (winner == 'X') return depth - 10; // Prefer delaying loss
            else return 0; // Draw
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < boardSize; i++) {
                for (int j = 0; j < boardSize; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = 'O';
                        int score = minimax(board, boardSize, depth + 1, false);
                        board[i][j] = ' ';
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
            return bestScore;

        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < boardSize; i++) {
                for (int j = 0; j < boardSize; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = 'X';
                        int score = minimax(board, boardSize, depth + 1, true);
                        board[i][j] = ' ';
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
    }

    private static Character checkWinner(char[][] board, int boardSize) {
        // Rows & columns
        for (int i = 0; i < boardSize; i++) {
            if (board[i][0] != ' ' && allEqual(board[i], boardSize)) return board[i][0];

            char[] col = new char[boardSize];
            for (int j = 0; j < boardSize; j++) col[j] = board[j][i];
            if (col[0] != ' ' && allEqual(col, boardSize)) return col[0];
        }

        // Diagonals
        char[] diag1 = new char[boardSize];
        char[] diag2 = new char[boardSize];
        for (int i = 0; i < boardSize; i++) {
            diag1[i] = board[i][i];
            diag2[i] = board[i][boardSize - 1 - i];
        }
        if (diag1[0] != ' ' && allEqual(diag1, boardSize)) return diag1[0];
        if (diag2[0] != ' ' && allEqual(diag2, boardSize)) return diag2[0];

        // Check draw
        boolean boardFull = true;
        for (int i = 0; i < boardSize; i++)
            for (int j = 0; j < boardSize; j++)
                if (board[i][j] == ' ')
                    boardFull = false;

        if (boardFull) return 'D'; // D for Draw

        return null; // Game still in progress
    }

    private static boolean allEqual(char[] arr, int size) {
        for (int i = 1; i < size; i++) {
            if (arr[i] != arr[0]) return false;
        }
        return true;
    }
}
