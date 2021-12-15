package it.aendrix.skywars.skywars;

import it.aendrix.skywars.items.Chest;

public class SkyWarsType {

    //Esempio Normal, Speed, Insane

    private String name;
    private int timeMax;
    private int pointOnJoin, getPointOnKill, getPointOnWin;
    private Chest[] chestsType;

    public SkyWarsType(String name, int timeMax, int pointOnJoin, int getPointOnKill, int getPointOnWin, Chest[] chestsType) {
        this.name = name;
        this.timeMax = timeMax;
        this.pointOnJoin = pointOnJoin;
        this.getPointOnKill = getPointOnKill;
        this.getPointOnWin = getPointOnWin;
        this.chestsType = chestsType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTimeMax() {
        return timeMax;
    }

    public void setTimeMax(int timeMax) {
        this.timeMax = timeMax;
    }

    public int getPointOnJoin() {
        return pointOnJoin;
    }

    public void setPointOnJoin(int pointOnJoin) {
        this.pointOnJoin = pointOnJoin;
    }

    public int getGetPointOnKill() {
        return getPointOnKill;
    }

    public void setGetPointOnKill(int getPointOnKill) {
        this.getPointOnKill = getPointOnKill;
    }

    public int getGetPointOnWin() {
        return getPointOnWin;
    }

    public void setGetPointOnWin(int getPointOnWin) {
        this.getPointOnWin = getPointOnWin;
    }

    public Chest[] getChestsType() {
        return chestsType;
    }

    public void setChestsType(Chest[] chestsType) {
        this.chestsType = chestsType;
    }
}
