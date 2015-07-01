package com.yannickschuchmann.peng.model.entities;

public class User {
    public int id;
    private int uid;
    private String nick;
    private String slogan;
    private int friendsCount;
    private int duelsCount;
    private int rank;

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }

    public void setFriendsCount(int count) {
        this.friendsCount = count;
    }

    public int getFriendsCount() {
        return this.friendsCount;
    }

    public void setDuelsCount(int count) {
        this.duelsCount = count;
    }

    public int getDuelsCount() {
        return this.duelsCount;
    }


}