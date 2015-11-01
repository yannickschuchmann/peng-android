package com.yannickschuchmann.peng.model.entities;

public class Duel {
    public int id;
    private String bet;
    private String status;
    private boolean myTurn;
    private Actor me;
    private Actor opponent;
    private Action myAction;
    private Action opponentAction;
    private boolean active;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    private String result;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public boolean isMyTurn() {
        return myTurn;
    }

    public void setMyTurn(boolean myTurn) {
        this.myTurn = myTurn;
    }

    public Actor getMe() {
        return me;
    }

    public void setMe(Actor me) {
        this.me = me;
    }

    public Actor getOpponent() {
        return opponent;
    }

    public void setOpponent(Actor opponent) {
        this.opponent = opponent;
    }

    public Action getMyAction() {
        return myAction;
    }

    public void setMyAction(Action myAction) {
        this.myAction = myAction;
    }

    public Action getOpponentAction() {
        return opponentAction;
    }

    public void setOpponentAction(Action opponentAction) {
        this.opponentAction = opponentAction;
    }
}