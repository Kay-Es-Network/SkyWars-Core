package it.aendrix.skywars.items;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Entity;
import org.bukkit.util.NumberConversions;
import org.bukkit.util.Vector;

@SerializableAs("BoundingBox")
public class BoundingBox implements Cloneable, ConfigurationSerializable {
    private double minX;

    private double minY;

    private double minZ;

    private double maxX;

    private double maxY;

    private double maxZ;

    
    public static BoundingBox of(Vector corner1, Vector corner2) {
        Validate.notNull(corner1, "Corner1 is null!");
        Validate.notNull(corner2, "Corner2 is null!");
        return new BoundingBox(corner1.getX(), corner1.getY(), corner1.getZ(), corner2.getX(), corner2.getY(), corner2.getZ());
    }

    
    public static BoundingBox of(Location corner1, Location corner2) {
        Validate.notNull(corner1, "Corner1 is null!");
        Validate.notNull(corner2, "Corner2 is null!");
        Validate.isTrue(Objects.equals(corner1.getWorld(), corner2.getWorld()), "Locations from different worlds!");
        return new BoundingBox(corner1.getX(), corner1.getY(), corner1.getZ(), corner2.getX(), corner2.getY(), corner2.getZ());
    }

    
    public static BoundingBox of(Block corner1, Block corner2) {
        Validate.notNull(corner1, "Corner1 is null!");
        Validate.notNull(corner2, "Corner2 is null!");
        Validate.isTrue(Objects.equals(corner1.getWorld(), corner2.getWorld()), "Blocks from different worlds!");
        int x1 = corner1.getX();
        int y1 = corner1.getY();
        int z1 = corner1.getZ();
        int x2 = corner2.getX();
        int y2 = corner2.getY();
        int z2 = corner2.getZ();
        int minX = Math.min(x1, x2);
        int minY = Math.min(y1, y2);
        int minZ = Math.min(z1, z2);
        int maxX = Math.max(x1, x2) + 1;
        int maxY = Math.max(y1, y2) + 1;
        int maxZ = Math.max(z1, z2) + 1;
        return new BoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
    }

    
    public static BoundingBox of(Block block) {
        Validate.notNull(block, "Block is null!");
        return new BoundingBox(block.getX(), block.getY(), block.getZ(), (block.getX() + 1), (block.getY() + 1), (block.getZ() + 1));
    }

    
    public static BoundingBox of(Vector center, double x, double y, double z) {
        Validate.notNull(center, "Center is null!");
        return new BoundingBox(center.getX() - x, center.getY() - y, center.getZ() - z, center.getX() + x, center.getY() + y, center.getZ() + z);
    }

    
    public static BoundingBox of(Location center, double x, double y, double z) {
        Validate.notNull(center, "Center is null!");
        return new BoundingBox(center.getX() - x, center.getY() - y, center.getZ() - z, center.getX() + x, center.getY() + y, center.getZ() + z);
    }

    public BoundingBox() {
        resize(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
    }

    public BoundingBox(double x1, double y1, double z1, double x2, double y2, double z2) {
        resize(x1, y1, z1, x2, y2, z2);
    }

    
    public BoundingBox resize(double x1, double y1, double z1, double x2, double y2, double z2) {
        NumberConversions.checkFinite(x1, "x1 not finite");
        NumberConversions.checkFinite(y1, "y1 not finite");
        NumberConversions.checkFinite(z1, "z1 not finite");
        NumberConversions.checkFinite(x2, "x2 not finite");
        NumberConversions.checkFinite(y2, "y2 not finite");
        NumberConversions.checkFinite(z2, "z2 not finite");
        this.minX = Math.min(x1, x2);
        this.minY = Math.min(y1, y2);
        this.minZ = Math.min(z1, z2);
        this.maxX = Math.max(x1, x2);
        this.maxY = Math.max(y1, y2);
        this.maxZ = Math.max(z1, z2);
        return this;
    }

    public double getMinX() {
        return this.minX;
    }

    public double getMinY() {
        return this.minY;
    }

    public double getMinZ() {
        return this.minZ;
    }

    
    public Vector getMin() {
        return new Vector(this.minX, this.minY, this.minZ);
    }

    public double getMaxX() {
        return this.maxX;
    }

    public double getMaxY() {
        return this.maxY;
    }

    public double getMaxZ() {
        return this.maxZ;
    }

    
    public Vector getMax() {
        return new Vector(this.maxX, this.maxY, this.maxZ);
    }

    public double getWidthX() {
        return this.maxX - this.minX;
    }

    public double getWidthZ() {
        return this.maxZ - this.minZ;
    }

    public double getHeight() {
        return this.maxY - this.minY;
    }

    public double getVolume() {
        return getHeight() * getWidthX() * getWidthZ();
    }

    public double getCenterX() {
        return this.minX + getWidthX() * 0.5D;
    }

    public double getCenterY() {
        return this.minY + getHeight() * 0.5D;
    }

    public double getCenterZ() {
        return this.minZ + getWidthZ() * 0.5D;
    }

    
    public Vector getCenter() {
        return new Vector(getCenterX(), getCenterY(), getCenterZ());
    }

    
    public BoundingBox copy(BoundingBox other) {
        Validate.notNull(other, "Other bounding box is null!");
        return resize(other.getMinX(), other.getMinY(), other.getMinZ(), other.getMaxX(), other.getMaxY(), other.getMaxZ());
    }

    
    public BoundingBox expand(double negativeX, double negativeY, double negativeZ, double positiveX, double positiveY, double positiveZ) {
        if (negativeX == 0.0D && negativeY == 0.0D && negativeZ == 0.0D && positiveX == 0.0D && positiveY == 0.0D && positiveZ == 0.0D)
            return this;
        double newMinX = this.minX - negativeX;
        double newMinY = this.minY - negativeY;
        double newMinZ = this.minZ - negativeZ;
        double newMaxX = this.maxX + positiveX;
        double newMaxY = this.maxY + positiveY;
        double newMaxZ = this.maxZ + positiveZ;
        if (newMinX > newMaxX) {
            double centerX = getCenterX();
            if (newMaxX >= centerX) {
                newMinX = newMaxX;
            } else if (newMinX <= centerX) {
                newMaxX = newMinX;
            } else {
                newMinX = centerX;
                newMaxX = centerX;
            }
        }
        if (newMinY > newMaxY) {
            double centerY = getCenterY();
            if (newMaxY >= centerY) {
                newMinY = newMaxY;
            } else if (newMinY <= centerY) {
                newMaxY = newMinY;
            } else {
                newMinY = centerY;
                newMaxY = centerY;
            }
        }
        if (newMinZ > newMaxZ) {
            double centerZ = getCenterZ();
            if (newMaxZ >= centerZ) {
                newMinZ = newMaxZ;
            } else if (newMinZ <= centerZ) {
                newMaxZ = newMinZ;
            } else {
                newMinZ = centerZ;
                newMaxZ = centerZ;
            }
        }
        return resize(newMinX, newMinY, newMinZ, newMaxX, newMaxY, newMaxZ);
    }

    
    public BoundingBox expand(double x, double y, double z) {
        return expand(x, y, z, x, y, z);
    }

    
    public BoundingBox expand(Vector expansion) {
        Validate.notNull(expansion, "Expansion is null!");
        double x = expansion.getX();
        double y = expansion.getY();
        double z = expansion.getZ();
        return expand(x, y, z, x, y, z);
    }

    
    public BoundingBox expand(double expansion) {
        return expand(expansion, expansion, expansion, expansion, expansion, expansion);
    }

    
    public BoundingBox expand(double dirX, double dirY, double dirZ, double expansion) {
        if (expansion == 0.0D)
            return this;
        if (dirX == 0.0D && dirY == 0.0D && dirZ == 0.0D)
            return this;
        double negativeX = (dirX < 0.0D) ? (-dirX * expansion) : 0.0D;
        double negativeY = (dirY < 0.0D) ? (-dirY * expansion) : 0.0D;
        double negativeZ = (dirZ < 0.0D) ? (-dirZ * expansion) : 0.0D;
        double positiveX = (dirX > 0.0D) ? (dirX * expansion) : 0.0D;
        double positiveY = (dirY > 0.0D) ? (dirY * expansion) : 0.0D;
        double positiveZ = (dirZ > 0.0D) ? (dirZ * expansion) : 0.0D;
        return expand(negativeX, negativeY, negativeZ, positiveX, positiveY, positiveZ);
    }

    
    public BoundingBox expand(Vector direction, double expansion) {
        Validate.notNull(direction, "Direction is null!");
        return expand(direction.getX(), direction.getY(), direction.getZ(), expansion);
    }

    
    public BoundingBox expand(BlockFace blockFace, double expansion) {
        Validate.notNull(blockFace, "Block face is null!");
        if (blockFace == BlockFace.SELF)
            return this;
        return expand(blockFace, expansion);
    }

    
    public BoundingBox expandDirectional(double dirX, double dirY, double dirZ) {
        return expand(dirX, dirY, dirZ, 1.0D);
    }

    
    public BoundingBox expandDirectional(Vector direction) {
        Validate.notNull(direction, "Expansion is null!");
        return expand(direction.getX(), direction.getY(), direction.getZ(), 1.0D);
    }


    public BoundingBox union(double posX, double posY, double posZ) {
        double newMinX = Math.min(this.minX, posX);
        double newMinY = Math.min(this.minY, posY);
        double newMinZ = Math.min(this.minZ, posZ);
        double newMaxX = Math.max(this.maxX, posX);
        double newMaxY = Math.max(this.maxY, posY);
        double newMaxZ = Math.max(this.maxZ, posZ);
        if (newMinX == this.minX && newMinY == this.minY && newMinZ == this.minZ && newMaxX == this.maxX && newMaxY == this.maxY && newMaxZ == this.maxZ)
            return this;
        return resize(newMinX, newMinY, newMinZ, newMaxX, newMaxY, newMaxZ);
    }

    
    public BoundingBox union(Vector position) {
        Validate.notNull(position, "Position is null!");
        return union(position.getX(), position.getY(), position.getZ());
    }

    
    public BoundingBox union(Location position) {
        Validate.notNull(position, "Position is null!");
        return union(position.getX(), position.getY(), position.getZ());
    }

    
    public BoundingBox union(BoundingBox other) {
        Validate.notNull(other, "Other bounding box is null!");
        if (contains(other))
            return this;
        double newMinX = Math.min(this.minX, other.minX);
        double newMinY = Math.min(this.minY, other.minY);
        double newMinZ = Math.min(this.minZ, other.minZ);
        double newMaxX = Math.max(this.maxX, other.maxX);
        double newMaxY = Math.max(this.maxY, other.maxY);
        double newMaxZ = Math.max(this.maxZ, other.maxZ);
        return resize(newMinX, newMinY, newMinZ, newMaxX, newMaxY, newMaxZ);
    }

    
    public BoundingBox intersection(BoundingBox other) {
        Validate.notNull(other, "Other bounding box is null!");
        Validate.isTrue(overlaps(other), "The bounding boxes do not overlap!");
        double newMinX = Math.max(this.minX, other.minX);
        double newMinY = Math.max(this.minY, other.minY);
        double newMinZ = Math.max(this.minZ, other.minZ);
        double newMaxX = Math.min(this.maxX, other.maxX);
        double newMaxY = Math.min(this.maxY, other.maxY);
        double newMaxZ = Math.min(this.maxZ, other.maxZ);
        return resize(newMinX, newMinY, newMinZ, newMaxX, newMaxY, newMaxZ);
    }

    
    public BoundingBox shift(double shiftX, double shiftY, double shiftZ) {
        if (shiftX == 0.0D && shiftY == 0.0D && shiftZ == 0.0D)
            return this;
        return resize(this.minX + shiftX, this.minY + shiftY, this.minZ + shiftZ,
                this.maxX + shiftX, this.maxY + shiftY, this.maxZ + shiftZ);
    }

    
    public BoundingBox shift(Vector shift) {
        Validate.notNull(shift, "Shift is null!");
        return shift(shift.getX(), shift.getY(), shift.getZ());
    }

    
    public BoundingBox shift(Location shift) {
        Validate.notNull(shift, "Shift is null!");
        return shift(shift.getX(), shift.getY(), shift.getZ());
    }

    private boolean overlaps(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        return (this.minX < maxX && this.maxX > minX &&
                this.minY < maxY && this.maxY > minY &&
                this.minZ < maxZ && this.maxZ > minZ);
    }

    public boolean overlaps(BoundingBox other) {
        Validate.notNull(other, "Other bounding box is null!");
        return overlaps(other.minX, other.minY, other.minZ, other.maxX, other.maxY, other.maxZ);
    }

    public boolean overlaps(Vector min, Vector max) {
        Validate.notNull(min, "Min is null!");
        Validate.notNull(max, "Max is null!");
        double x1 = min.getX();
        double y1 = min.getY();
        double z1 = min.getZ();
        double x2 = max.getX();
        double y2 = max.getY();
        double z2 = max.getZ();
        return overlaps(Math.min(x1, x2), Math.min(y1, y2), Math.min(z1, z2),
                Math.max(x1, x2), Math.max(y1, y2), Math.max(z1, z2));
    }

    public boolean contains(double x, double y, double z) {
        return (x >= this.minX && x < this.maxX &&
                y >= this.minY && y < this.maxY &&
                z >= this.minZ && z < this.maxZ);
    }

    public boolean contains(Vector position) {
        Validate.notNull(position, "Position is null!");
        return contains(position.getX(), position.getY(), position.getZ());
    }

    private boolean contains(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        return (this.minX <= minX && this.maxX >= maxX &&
                this.minY <= minY && this.maxY >= maxY &&
                this.minZ <= minZ && this.maxZ >= maxZ);
    }

    public boolean contains(BoundingBox other) {
        Validate.notNull(other, "Other bounding box is null!");
        return contains(other.minX, other.minY, other.minZ, other.maxX, other.maxY, other.maxZ);
    }

    public boolean contains(Vector min, Vector max) {
        Validate.notNull(min, "Min is null!");
        Validate.notNull(max, "Max is null!");
        double x1 = min.getX();
        double y1 = min.getY();
        double z1 = min.getZ();
        double x2 = max.getX();
        double y2 = max.getY();
        double z2 = max.getZ();
        return contains(Math.min(x1, x2), Math.min(y1, y2), Math.min(z1, z2),
                Math.max(x1, x2), Math.max(y1, y2), Math.max(z1, z2));
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        long temp = Double.doubleToLongBits(this.maxX);
        result = 31 * result + (int)(temp ^ temp >>> 32L);
        temp = Double.doubleToLongBits(this.maxY);
        result = 31 * result + (int)(temp ^ temp >>> 32L);
        temp = Double.doubleToLongBits(this.maxZ);
        result = 31 * result + (int)(temp ^ temp >>> 32L);
        temp = Double.doubleToLongBits(this.minX);
        result = 31 * result + (int)(temp ^ temp >>> 32L);
        temp = Double.doubleToLongBits(this.minY);
        result = 31 * result + (int)(temp ^ temp >>> 32L);
        temp = Double.doubleToLongBits(this.minZ);
        result = 31 * result + (int)(temp ^ temp >>> 32L);
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof BoundingBox))
            return false;
        BoundingBox other = (BoundingBox)obj;
        if (Double.doubleToLongBits(this.maxX) != Double.doubleToLongBits(other.maxX))
            return false;
        if (Double.doubleToLongBits(this.maxY) != Double.doubleToLongBits(other.maxY))
            return false;
        if (Double.doubleToLongBits(this.maxZ) != Double.doubleToLongBits(other.maxZ))
            return false;
        if (Double.doubleToLongBits(this.minX) != Double.doubleToLongBits(other.minX))
            return false;
        if (Double.doubleToLongBits(this.minY) != Double.doubleToLongBits(other.minY))
            return false;
        if (Double.doubleToLongBits(this.minZ) != Double.doubleToLongBits(other.minZ))
            return false;
        return true;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("BoundingBox [minX=");
        builder.append(this.minX);
        builder.append(", minY=");
        builder.append(this.minY);
        builder.append(", minZ=");
        builder.append(this.minZ);
        builder.append(", maxX=");
        builder.append(this.maxX);
        builder.append(", maxY=");
        builder.append(this.maxY);
        builder.append(", maxZ=");
        builder.append(this.maxZ);
        builder.append("]");
        return builder.toString();
    }

    
    public BoundingBox clone() {
        try {
            return (BoundingBox)super.clone();
        } catch (CloneNotSupportedException e) {
            throw new Error(e);
        }
    }

    
    public Map<String, Object> serialize() {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("minX", Double.valueOf(this.minX));
        result.put("minY", Double.valueOf(this.minY));
        result.put("minZ", Double.valueOf(this.minZ));
        result.put("maxX", Double.valueOf(this.maxX));
        result.put("maxY", Double.valueOf(this.maxY));
        result.put("maxZ", Double.valueOf(this.maxZ));
        return result;
    }

    
    public static BoundingBox deserialize(Map<String, Object> args) {
        double minX = 0.0D;
        double minY = 0.0D;
        double minZ = 0.0D;
        double maxX = 0.0D;
        double maxY = 0.0D;
        double maxZ = 0.0D;
        if (args.containsKey("minX"))
            minX = ((Number)args.get("minX")).doubleValue();
        if (args.containsKey("minY"))
            minY = ((Number)args.get("minY")).doubleValue();
        if (args.containsKey("minZ"))
            minZ = ((Number)args.get("minZ")).doubleValue();
        if (args.containsKey("maxX"))
            maxX = ((Number)args.get("maxX")).doubleValue();
        if (args.containsKey("maxY"))
            maxY = ((Number)args.get("maxY")).doubleValue();
        if (args.containsKey("maxZ"))
            maxZ = ((Number)args.get("maxZ")).doubleValue();
        return new BoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
    }
}
