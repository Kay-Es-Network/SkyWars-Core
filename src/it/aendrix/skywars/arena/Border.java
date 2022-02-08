package it.aendrix.skywars.arena;

import java.util.*;

import it.aendrix.skywars.items.BoundingBox;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;

public class Border {

    private final int x;
    private final int y;
    private final int z;
    private final int x2;
    private final int y2;
    private final int z2;
    private final String world;

    public Border(String world, int x, int y, int z, int x2, int y2, int z2) {
        this.world = world;
        this.x = Math.min(x,x2);
        this.y = Math.min(y, y2);
        this.z = Math.min(z, z2);
        this.x2 = Math.max(x,x2);
        this.y2 = Math.max(y, y2);
        this.z2 = Math.max(z, z2);
    }

    public Border(Location location, Location location2) {
        this(Objects.requireNonNull(location.getWorld()).getName(), ((int) location.getX()), ((int) location.getY()),
                ((int) location.getZ()), ((int) location2.getX()), ((int) location2.getY()), ((int) location2.getZ()));
    }

    public Integer[] getRandomLocs() {
        Random r = new Random();
        return new Integer[] {r.nextInt(x2 - x + 1) + x, y2, r.nextInt(z2 - z + 1) + z};
    }

    public boolean isInRegion(Location loc) {
        if (!Objects.requireNonNull(loc.getWorld()).getName().equals(world)) return false;
        int cx = loc.getBlockX();
        int cy = loc.getBlockY();
        int cz = loc.getBlockZ();
        return (cx >= x && cx <= x2) && (cy >= y && cy <= y2) && (cz >= z && cz <= z2);
    }

    public int radiusX() {
        return Math.abs(x-x2)/2;
    }

    public int radiusY() {
        return Math.abs(y-y2)/2;
    }

    public int radiusZ() {
        return Math.abs(z-z2)/2;
    }

    public Collection<Entity> getEntities() {
        return this.getCenter().getWorld().getNearbyEntities(this.getCenter(), this.radiusX(), this.radiusY(), this.radiusZ());
    }

    public ArrayList<Location> getBlocks(Material type) {
        World w = Bukkit.getWorld(world);
        ArrayList <Location> array = new ArrayList<>();
        for (int x3 = x; x3 <= x2; x3++) {
            for (int y3 = y; y3 <= y2; y3++) {
                for (int z3 = z; z3 <= z2; z3++) {
                    assert w != null;
                    Block b = w.getBlockAt(x3, y3, z3);
                    if (b.getType() == type) {
                        array.add(b.getLocation());
                    }
                }
            }
        }
        return array;
    }

    public World getWorld() {
        return Bukkit.getWorld(world);
    }

    public Location getLesserCorner() {
        if (y<y2)
            return new Location(Bukkit.getWorld(world), x, y, z);
        else
            return new Location(Bukkit.getWorld(world), x, y2, z);
    }

    public Location getGreaterCorner() {
        if (y>y2)
            return new Location(Bukkit.getWorld(world), x, y, z);
        else
            return new Location(Bukkit.getWorld(world), x, y2, z);
    }

    public Location getCenter() {
        BoundingBox box = new BoundingBox(x, y, z, x2, y2, z2);
        return new Location(this.getWorld(), box.getCenterX(), box.getCenterY(), box.getCenterZ());
    }

    @Override
    public String toString() {
        return "Bound{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", x2=" + x2 +
                ", y2=" + y2 +
                ", z2=" + z2 +
                ", world='" + world + '\'' +
                '}';
    }

    public int getMinY() {
        if (y<y2) return y;
        else return y2;
    }

    public double getMaxY() {
        if (y>y2) return y;
        else return y2;
    }

    public void clearDrops() {
        Location c = getCenter();
        for (Entity e : this.getEntities())
            if (e instanceof Item) e.remove();
    }


}
