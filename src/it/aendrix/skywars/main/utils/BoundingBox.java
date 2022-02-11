package it.aendrix.skywars.main.utils;

import org.bukkit.configuration.serialization.SerializableAs;

@SerializableAs("BoundingBox")
public class BoundingBox implements Cloneable {
    private double minX;
    private double minY;
    private double minZ;
    private double maxX;
    private double maxY;
    private double maxZ;

    public BoundingBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
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

    public double getCenterX() {
        return this.minX + getWidthX() * 0.5D;
    }

    public double getCenterY() {
        return this.minY + getHeight() * 0.5D;
    }

    public double getCenterZ() {
        return this.minZ + getWidthZ() * 0.5D;
    }

    public int hashCode() {
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
        if (!(obj instanceof BoundingBox other))
            return false;
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
        return Double.doubleToLongBits(this.minZ) == Double.doubleToLongBits(other.minZ);
    }

    public String toString() {
        return "BoundingBox [minX=" +
                this.minX +
                ", minY=" +
                this.minY +
                ", minZ=" +
                this.minZ +
                ", maxX=" +
                this.maxX +
                ", maxY=" +
                this.maxY +
                ", maxZ=" +
                this.maxZ +
                "]";
    }

    public BoundingBox clone() {
        try {
            return (BoundingBox)super.clone();
        } catch (CloneNotSupportedException e) {
            throw new Error(e);
        }
    }

}
