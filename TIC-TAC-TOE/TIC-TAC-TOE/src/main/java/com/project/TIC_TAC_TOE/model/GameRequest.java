// GameRequest.java
package com.project.TIC_TAC_TOE.model;

public class GameRequest {
    private int boardSize;
    private boolean vsComputer;

    // Getters and setters
    public int getBoardSize() { return boardSize; }
    public void setBoardSize(int boardSize) { this.boardSize = boardSize; }
    public boolean isVsComputer() { return vsComputer; }
    public void setVsComputer(boolean vsComputer) { this.vsComputer = vsComputer; }
}