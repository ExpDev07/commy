package com.github.expdev07.commy.core.message;

import com.github.expdev07.commy.core.Message;

/**
 * A location
 */
public class LocationMessage implements Message {

    private String world;

    private double x;
    private double y;
    private double z;

    private float yaw;
    private float pitch;

    public LocationMessage(String world, double x, double y, double z, float yaw, float pitch) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public String getWorld() {
        return world;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }
}
