// MoveRequest.java
package com.project.TIC_TAC_TOE.model;

public class MoveRequest {
    private int row;
    private int col;
    private char player;

    // Getters and setters
    public int getRow() { return row; }
    public void setRow(int row) { this.row = row; }
    public int getCol() { return col; }
    public void setCol(int col) { this.col = col; }
    public char getPlayer() { return player; }
    public void setPlayer(char player) { this.player = player; }
}