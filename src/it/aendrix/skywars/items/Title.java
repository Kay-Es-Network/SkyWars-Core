package it.aendrix.skywars.items;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;


public class Title {

    private String title = "";
    private String subtitle = "";
    private int fadeInTime = 0;
    private int stayTime = 1;
    private int fadeOutTime = 0;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public int getFadeInTime() {
        return fadeInTime;
    }

    public void setFadeInTime(int fadeInTime) {
        this.fadeInTime = fadeInTime;
    }

    public int getStayTime() {
        return stayTime;
    }

    public void setStayTime(int stayTime) {
        this.stayTime = stayTime;
    }

    public int getFadeOutTime() {
        return fadeOutTime;
    }

    public void setFadeOutTime(int fadeOutTime) {
        this.fadeOutTime = fadeOutTime;
    }

    public void send(Player p) {
        p.sendTitle(title, subtitle, fadeInTime*20, stayTime*20, fadeOutTime*20);
    }
}