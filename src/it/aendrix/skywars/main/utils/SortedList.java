package it.aendrix.skywars.main.utils;

import it.aendrix.skywars.main.utils.utils;
import org.bukkit.entity.Player;

import java.io.Serializable;
import java.util.ArrayList;

public class SortedList extends ArrayList implements Serializable {
    private Player[] keys;

    private int[] set;

    public SortedList() {
        int initialSize = 0;
        this.keys = new Player[initialSize];
        this.set = new int[initialSize];
    }

    public boolean containsKey(Player key) {
        for (Player o : this.keys) {
            if (o != null && o.getName().equalsIgnoreCase(key.getName()))
                return true;
        }
        return false;
    }

    public int positionKey(Player key) {
        for (int i = 0; i < this.keys.length; i++) {
            if (this.keys[i] != null && this.keys[i].getName().equalsIgnoreCase(key.getName()))
                return i;
        }
        return -1;
    }

    public int getValue(Player key) {
        int pos = positionKey(key);
        if (pos == -1)
            return 0;
        return this.set[pos];
    }

    public void insert(Player key, int value) {
        int pos = positionKey(key);
        if (pos == 0) {
            this.set[pos] = value;
        } else if (pos == -1) {
            insertNotExists(key, value);
        } else {
            utils.removeFromArray(this.keys, key);
            insertNotExists(key, value);
        }
    }

    public int size() {
        return this.set.length;
    }

    private void insertNotExists(Player key, int value) {
        int pos;
        for (pos = 0; pos < this.set.length &&
                value <= this.set[pos]; pos++);
        if (pos == this.set.length - 1 && value < this.set[pos])
            pos = this.set.length;
        Player[] newKeys = new Player[this.set.length + 1];
        int[] newSet = new int[this.set.length + 1];
        newKeys[pos] = key;
        newSet[pos] = value;
        for (int i = 0; i < newKeys.length; i++) {
            if (i < pos) {
                newKeys[i] = this.keys[i];
                newSet[i] = this.set[i];
            } else if (i > pos) {
                newKeys[i + 1] = this.keys[i];
                newSet[i + 1] = this.set[i];
            }
        }
        this.keys = newKeys;
        this.set = newSet;
    }
}
