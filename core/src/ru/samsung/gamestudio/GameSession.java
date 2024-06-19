package ru.samsung.gamestudio;

import com.badlogic.gdx.utils.TimeUtils;

import ru.samsung.gamestudio.managers.MemoryManager;

import java.util.ArrayList;
import java.util.Random;


public class GameSession {

    public GameState state;
    long nextTrashSpawnTime;
    long nextKitSpawnTime;
    long sessionStartTime;
    long startFreezeTime;
    long startReloadFreezeTime;
    static boolean freezing;
    long pauseStartTime;
    private int score;
    int destructedTrashNumber;

    public GameSession() {
    }

    public void startGame() {
        state = GameState.PLAYING;
        score = 0;
        destructedTrashNumber = 0;
        startFreezeTime = TimeUtils.millis();
        startReloadFreezeTime = TimeUtils.millis();
        freezing = false;
        sessionStartTime = TimeUtils.millis();
        nextTrashSpawnTime = sessionStartTime + (long) (GameSettings.STARTING_TRASH_APPEARANCE_COOL_DOWN
                * getTrashPeriodCoolDown());
        nextKitSpawnTime = sessionStartTime + (long) (GameSettings.STARTING_KIT_APPEARANCE_COOL_DOWN + new Random().nextInt(5000) * getKitPeriodCoolDown());
    }

    public void pauseGame() {
        state = GameState.PAUSED;
        pauseStartTime = TimeUtils.millis();
    }

    public void resumeGame() {
        state = GameState.PLAYING;
        sessionStartTime += TimeUtils.millis() - pauseStartTime;
    }

    public void freeze(){
        freezing = true;
        GameSettings.TRASH_VELOCITY = 5;
        startFreezeTime = TimeUtils.millis();
    }

    public void stopFreeze(){
        freezing = false;
        GameSettings.TRASH_VELOCITY = 20;
        startReloadFreezeTime = TimeUtils.millis();
    }

    public boolean isFreezing(){
        return freezing;
    }

    public int freezingTime() {
        return (int) ((11000 - (TimeUtils.millis() - startFreezeTime)) / 1000);
    }

    public int reloadFreezingTime() {
        return (int) ((16000 - (TimeUtils.millis() - startReloadFreezeTime)) / 1000);
    }

    public void endGame() {
        updateScore();
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

    public void destructionRegistration() {
        destructedTrashNumber += 1;
    }

    public void updateScore() {
        score = (int) (TimeUtils.millis() - sessionStartTime) / 100 + destructedTrashNumber * 100;
    }

    public int getScore() {
        return score;
    }

    public boolean shouldSpawnTrash() {
        if (nextTrashSpawnTime <= TimeUtils.millis()) {
            nextTrashSpawnTime = TimeUtils.millis() + (long) (GameSettings.STARTING_TRASH_APPEARANCE_COOL_DOWN
                    * getTrashPeriodCoolDown());
            return true;
        }
        return false;
    }

    public boolean shouldSpawnKit() {
        if (nextKitSpawnTime <= TimeUtils.millis() && !shouldSpawnTrash()) {
            nextKitSpawnTime = TimeUtils.millis() + (long) (GameSettings.STARTING_KIT_APPEARANCE_COOL_DOWN + new Random().nextInt(5000) * getKitPeriodCoolDown());
            return true;
        }
        return false;
    }

    private float getTrashPeriodCoolDown() {
        return (float) Math.exp(-0.001 * (TimeUtils.millis() - sessionStartTime + 1) / 1000);
    }

    private float getKitPeriodCoolDown() {
        return (float) Math.exp(-0.001 * (TimeUtils.millis() - sessionStartTime + 1) / 1000);
    }
}
