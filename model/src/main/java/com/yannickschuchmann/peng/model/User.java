package com.yannickschuchmann.peng.model;

public class User {
    private String nick;
    private String slogan;
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

    public void setRank(int rank) { this.rank = rank; }

    public String getRank() {
        return rank + ". Platz";
    }
}