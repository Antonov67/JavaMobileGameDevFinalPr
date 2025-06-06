package com.example.fruitninja;

import com.example.fruitninja.enums.GameState;
import com.example.fruitninja.managers.MemoryManager;

import java.util.ArrayList;

public class GameSession {

    private GameState state;
    private int score;


    public GameSession() {
    }

    public GameState getState() {
        return state;
    }

    public void startGame() {
        state = GameState.PLAYING;
        score = 0;
    }

    public void setScore(int score) {
        this.score = score;
    }


    public void pauseGame() {
        state = GameState.PAUSED;

    }

    public void resumeGame() {
        state = GameState.PLAYING;

    }

    public void endGame() {
        state = GameState.ENDED;
        ArrayList<Integer> recordsTable = MemoryManager.loadRecordsTable();
        if (recordsTable == null) {
            recordsTable = new ArrayList<>();
        }
        int foundIdx = 0;
        for (; foundIdx < recordsTable.size(); foundIdx++) {
            if (recordsTable.get(foundIdx) < getScore()) break;
        }
        recordsTable.add(foundIdx, getScore());
        MemoryManager.saveTableOfRecords(recordsTable);
    }

    public int getScore() {
        return score;
    }
}
