package it.aendrix.skywars.arena;

import it.aendrix.skywars.items.BoundingBox;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

public class Border implements Serializable {
    private final int x;
    private final int y;
    private final int z;
    private final int x2;
    private final int y2;
    private final int z2;
    private final String world;

    public Border(String world, int x, int y, int z, int x2, int y2, int z2) {
        this.world = world;
        this.x = Math.min(x, x2);
        this.y = Math.min(y, y2);
        this.z = Math.min(z, z2);
        this.x2 = Math.max(x, x2);
        this.y2 = Math.max(y, y2);
        this.z2 = Math.max(z, z2);
    }

    public Border(Location location, Location location2) {
        this(location.getWorld().getName(), (int)location.getX(), (int)location.getY(),
                (int)location.getZ(), (int)location2.getX(), (int)location2.getY(), (int)location2.getZ());
    }

    public Integer[] getRandomLocs() {
        Random r = new Random();
        return new Integer[] {r.nextInt(x2 - x + 1) + x, y2, r.nextInt(z2 - z + 1) + z};
    }

    public boolean isInRegion(Location loc) {
        if (!Objects.requireNonNull(loc.getWorld()).getName().equals(this.world))
            return false;
        int cx = loc.getBlockX();
        int cy = loc.getBlockY();
        int cz = loc.getBlockZ();
        return (cx >= this.x && cx <= this.x2 && cy >= this.y && cy <= this.y2 && cz >= this.z && cz <= this.z2);
    }

    public int radiusX() {
        return Math.abs(this.x - this.x2) / 2;
    }

    public int radiusY() {
        return Math.abs(this.y - this.y2) / 2;
    }

    public int radiusZ() {
        return Math.abs(this.z - this.z2) / 2;
    }

    public Collection<Entity> getEntities() {
        return getCenter().getWorld().getNearbyEntities(getCenter(), radiusX(), radiusY(), radiusZ());
    }

    public ArrayList<Location> getBlocks(Material type) {
        World w = Bukkit.getWorld(this.world);
        ArrayList<Location> array = new ArrayList<>();
        for (int x3 = this.x; x3 <= this.x2; x3++) {
            for (int y3 = this.y; y3 <= this.y2; y3++) {
                for (int z3 = this.z; z3 <= this.z2; z3++) {
                    assert w != null;
                    Block b = w.getBlockAt(x3, y3, z3);
                    if (b.getType() == type)
                        array.add(b.getLocation());
                }
            }
        }
        return array;
    }

    public World getWorld() {
        return Bukkit.getWorld(this.world);
    }

    public Location getLesserCorner() {
        if (this.y < this.y2)
            return new Location(Bukkit.getWorld(this.world), this.x, this.y, this.z);
        return new Location(Bukkit.getWorld(this.world), this.x, this.y2, this.z);
    }

    public Location getGreaterCorner() {
        if (this.y > this.y2)
            return new Location(Bukkit.getWorld(this.world), this.x, this.y, this.z);
        return new Location(Bukkit.getWorld(this.world), this.x, this.y2, this.z);
    }

    public Location getCenter() {
        BoundingBox box = new BoundingBox(this.x, this.y, this.z, this.x2, this.y2, this.z2);
        return new Location(getWorld(), box.getCenterX(), box.getCenterY(), box.getCenterZ());
    }

    public String toString() {
        return "Bound{x=" + this.x + ", y=" + this.y + ", z=" + this.z + ", x2=" + this.x2 + ", y2=" + this.y2 + ", z2=" + this.z2 + ", world='" + this.world + "'}";
    }

    public int getMinY() {
        return Math.min(this.y, this.y2);
    }

    public double getMaxY() {
        return Math.max(this.y, this.y2);
    }

    public void clearDrops() {
        Location c = getCenter();
        for (Entity e : getEntities()) {
            if (e instanceof org.bukkit.entity.Item)
                e.remove();
        }
    }
}
