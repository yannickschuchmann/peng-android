package com.yannickschuchmann.peng.model.entities;

public class User {
    public int id;
    private int uid;
    private String nick;
    private String slogan;
    private int friendsCount;
    private int duelsCount;
    private int rank;
    private String characterName;

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

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
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

    public String getCharacterName() {
        String cn = this.characterName;
        return cn != null && !cn.equals("") ? cn : "medic";
    }

    public void setCharacterName(String value) {
        this.characterName = value;
    }

}
