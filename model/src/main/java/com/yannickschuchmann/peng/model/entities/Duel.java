package com.yannickschuchmann.peng.model.entities;

public class Duel {
    public int id;
    private String bet;
    private String status;

    public String getBet() {
        return bet;
    }

    public void setBet(String bet) {
        this.bet = bet;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}