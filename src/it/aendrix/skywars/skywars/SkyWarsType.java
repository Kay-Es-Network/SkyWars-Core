package it.aendrix.skywars.skywars;

import it.aendrix.skywars.items.Chest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class SkyWarsType implements Serializable {
    public static HashMap<String, SkyWarsType> types;

    private String name;

    private int timeMax;

    private int pointOnJoin;

    private int pointOnKill;

    private int pointOnWin;

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
            for (int i = 0; i < 5; i++)
                this.chestsType[i] = new Chest(2, 6, i + 1, new ArrayList<>());
        }
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTimeMax() {
        return this.timeMax;
    }

    public void setTimeMax(int timeMax) {
        this.timeMax = timeMax;
    }

    public int getPointOnJoin() {
        return this.pointOnJoin;
    }

    public void setPointOnJoin(int pointOnJoin) {
        this.pointOnJoin = pointOnJoin;
    }

    public int getPointOnKill() {
        return this.pointOnKill;
    }

    public void setPointOnKill(int getPointOnKill) {
        this.pointOnKill = getPointOnKill;
    }

    public int getPointOnWin() {
        return this.pointOnWin;
    }

    public void setPointOnWin(int getPointOnWin) {
        this.pointOnWin = getPointOnWin;
    }

    public Chest[] getChestsType() {
        return this.chestsType;
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
