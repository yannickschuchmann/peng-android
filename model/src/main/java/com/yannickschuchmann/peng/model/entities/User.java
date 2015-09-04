package com.yannickschuchmann.peng.model.entities;

import java.util.List;

public class User {
    public String id;
    private String uid;
    private String nick;
    private String slogan;
    private String first_name;
    private String last_name;
    private String email;
//    private String picture;
    private int friendsCount;
    private int duelsCount;
    private int rank;
    private String characterName;
    private int characterOrder;
    public int characterId;
    private List<Duel> lastDuels;
    private List<Duel> openDuels;

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

    public int getId() { return Integer.valueOf(id); }

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

    public int getCharacterOrder() {
        return this.characterOrder;
    }

    public void setCharacterOrder(int value) {
        this.characterOrder = value;
    }

    public void setLastDuels(List<Duel> value) { this.lastDuels = value;}

    public List<Duel> getLastDuels() { return this.lastDuels; }

    public void setOpenDuels(List<Duel> value) { this.openDuels = value;}

    public List<Duel> getOpenDuels() { return this.openDuels; }

}
