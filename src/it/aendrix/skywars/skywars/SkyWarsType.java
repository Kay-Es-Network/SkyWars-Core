package it.aendrix.skywars.skywars;

import it.aendrix.skywars.items.Chest;
import it.aendrix.skywars.main.utils;

import java.util.ArrayList;
import java.util.HashMap;

public class SkyWarsType {

    public static HashMap<String, SkyWarsType> types;

    private String name;
    private int timeMax;
    private int pointOnJoin, pointOnKill, pointOnWin;
    private Chest[] chestsType;

    public SkyWarsType(String name, int timeMax, int pointOnJoin, int pointOnKill, int pointOnWin, Chest[] chestsType) {
        this.name = name;
        this.timeMax = timeMax;
        this.pointOnJoin = pointOnJoin;
        this.pointOnKill = pointOnKill;
        this.pointOnWin = pointOnWin;
        this.chestsType = chestsType;
        if (this.chestsType == null) {
            this.chestsType = new Chest[5];
            for (int i = 0; i<5; i++) {
                this.chestsType[i] = new Chest(2,6,i+1,new ArrayList<>());
            }
        }
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

    public int getPointOnKill() {
        return pointOnKill;
    }

    public void setPointOnKill(int getPointOnKill) {
        this.pointOnKill = getPointOnKill;
    }

    public int getPointOnWin() {
        return pointOnWin;
    }

    public void setPointOnWin(int getPointOnWin) {
        this.pointOnWin = getPointOnWin;
    }

    public Chest[] getChestsType() {
        return chestsType;
    }

    public void setChestsType(Chest[] chestsType) {
        this.chestsType = chestsType;
    }

    public static HashMap<String, SkyWarsType> getTypes() {
        return types;
    }

    public static void setTypes(HashMap<String, SkyWarsType> types) {
        SkyWarsType.types = types;
    }

}
