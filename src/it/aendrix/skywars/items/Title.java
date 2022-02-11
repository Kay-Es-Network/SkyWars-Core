package it.aendrix.skywars.items;

import org.bukkit.entity.Player;

import java.io.Serializable;

public class Title implements Serializable {
    private String title = "";

    private String subtitle = "";

    private int fadeInTime = 0;

    private int stayTime = 1;

    private int fadeOutTime = 0;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return this.subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public int getFadeInTime() {
        return this.fadeInTime;
    }

    public void setFadeInTime(int fadeInTime) {
        this.fadeInTime = fadeInTime;
    }

    public int getStayTime() {
        return this.stayTime;
    }

    public void setStayTime(int stayTime) {
        this.stayTime = stayTime;
    }

    public int getFadeOutTime() {
        return this.fadeOutTime;
    }

    public void setFadeOutTime(int fadeOutTime) {
        this.fadeOutTime = fadeOutTime;
    }

    public void send(Player p) {
        p.sendTitle(this.title, this.subtitle, this.fadeInTime * 20, this.stayTime * 20, this.fadeOutTime * 20);
    }
}
