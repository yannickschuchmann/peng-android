package com.yannickschuchmann.peng.model.entities;

/**
 * Created by yannick on 10.07.15.
 */
public class Message {
    private int user_id;
    private int duel_id;
    private int action;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getDuel_id() {
        return duel_id;
    }

    public void setDuel_id(int duel_id) {
        this.duel_id = duel_id;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }
}
