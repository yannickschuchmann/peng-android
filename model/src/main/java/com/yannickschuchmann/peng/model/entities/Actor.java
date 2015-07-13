package com.yannickschuchmann.peng.model.entities;

/**
 * Created by yannick on 13.07.15.
 */
public class Actor {
    private int id;
    private int userId;
    private int duelId;
    private int hitPoints;
    private int shots;
    private String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDuelId() {
        return duelId;
    }

    public void setDuelId(int duelId) {
        this.duelId = duelId;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

    public int getShots() {
        return shots;
    }

    public void setShots(int shots) {
        this.shots = shots;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
