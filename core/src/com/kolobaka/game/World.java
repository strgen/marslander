package com.kolobaka.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

import java.lang.Math;

public class World {
    // World's physics laws
    public final static float G = 3.711f; // Meters per sec squared
    public final static int HEIGHT = 3000;
    public final static int WIDHT = 7000;
    public final static int MAX_ANGLE = 90;
    public final static int MAX_ANGLE_CHANGE_PER_TICK = 15;
    public final static int MAX_THRUST = 4;
    public final static int MIN_THRUST = 0;
    public final static int MAX_THRUST_CHANGE_PER_TICK = 1;
    public final static int MAX_LANDING_ANGLE = 0;
    public final static int MAX_LANDING_SPEED_H = 20;
    public final static int MAX_LANDING_SPEED_V = 40;


    public static Surface surface;
    public static Ship ship;

    public World(Surface surface, Ship ship) {
        this.surface = surface;
        this.ship = ship;
    }

    public void iterate(int desiredThrust, int desiredRotation) {
        // Calculate how angle will change
        int angleChangeValue = desiredRotation - ship.rotation;
        // Calculate how thrust will change
        int thrustChangeValue = Math.abs(desiredThrust - ship.thrust);
        int thrustChangeSign = Integer.signum(desiredThrust - ship.thrust);

        // Do not allow rotation larger than the limit
        int angleChange = Math.min(Math.abs(angleChangeValue), MAX_ANGLE_CHANGE_PER_TICK) * Integer.signum(angleChangeValue);
        // Do not allow thrust change larger the the limit
        int thrustChange = Math.min(thrustChangeValue, MAX_THRUST_CHANGE_PER_TICK) * thrustChangeSign;

        // Calculate rotation change
        int newRotation = ship.rotation + angleChange;
        // Ensure rotation does not exceed allowed angles
        newRotation = Math.max(-MAX_ANGLE, newRotation); // Do not allow tilt larger than -90
        newRotation = Math.min(MAX_ANGLE, newRotation);  // Do not allow tilt larger than 90
        ship.rotation = newRotation;

        // Calculate thrust change
        int newThrust = ship.thrust + thrustChange;
        // Ensure thrust does not exceed allowed limits
        newThrust = Math.max(MIN_THRUST, newThrust);
        newThrust = Math.min(MAX_THRUST, newThrust);
        // Ensure thrust is zero when ship is out of fuel
        if (ship.fuel <= 0) {
            newThrust = 0;
        }
        // Assign newly calculated thrust to the ship
        ship.thrust = newThrust;

        // Calculate thrust change
        int normalizedRotation = ship.rotation + 90; // Normalize rotation regarding SIN circle

        float thrustV = (float) (ship.thrust * Math.sin(Math.toRadians(normalizedRotation)));
        float thrustH = (float) (ship.thrust * Math.cos(Math.toRadians(normalizedRotation)));

        // Calculate speed
        ship.speed.y = ship.speed.y + thrustV - G;
        ship.speed.x = ship.speed.x + thrustH;

        // Calculate fuel consumption
        if (ship.fuel > 0) {
            ship.fuel -= ship.thrust;
            if (ship.fuel < 0) {
                ship.fuel = 0;
            }
        }

        // Calculate position
        ship.position.x += ship.speed.x;
        ship.position.y += ship.speed.y;
    }

    public boolean isSuccessfulLanding() {
        if (Math.abs(ship.rotation) == MAX_LANDING_ANGLE &&
            Math.abs(ship.speed.x) == MAX_LANDING_SPEED_H &&
            Math.abs(ship.speed.y) == MAX_LANDING_SPEED_V) {
            return true;
        } else {
            return false;
        }
    }

    public checkIntersection() {
        int normalizedRotation = ship.rotation + 90;
        int normalizedRotationRad = Math.toRadians(normalizedRotation);

        float shipEndY = (float) (Math.sin(normalizedRotationRad);
        float shipEndX = (float) (Math.cos(normalizedRotationRad);

        float shipStart = new Vector2(ship.x, ship.y);
        float shipEnd = new Vector2(shipEndX, shipEndY);

        double a1 = shipEnd.y - shipStart.y;
        double b1 = shipStart.x - shipEnd.x;
        double c1 = a1 * shipStart.x + b1 * shipStart.y;

        for (int i = 1; i < surface.points.length; i++) {
            Vector2 lineStart = new Vector2(surface.points[i-1].x, surface.points[i-1].y);
            Vector2 lineEnd = new Vector2(surface.points[i].x, surface.points[i].y);

            double a2 = lineEnd.y - lineStart.y;
            double b2 = lineStart.x - lineEnd.y;
            double c2 = a2 * lineStart.x + b2 * lineStart.y;

            double delta = a1 * b2 - a2 * b1;
            double x = (b2 * c1 - b1 * c2) / delta;
            double y = (a1 * c2 - a2 * c1) / delta;

            if (x != Double.POSITIVE_INFINITY && 
                x != Double.NEGATIVE_INFINITY &&
                y != Double.POSITIVE_INFINITY &&
                y != Double.NEGATIVE_INFINITY) {
                return true;
            }
        }

        return false;
    }

    // Vector G { x: 0, y: 3.711 }
    // Points in the world
    public static class Ship {
        public int fuel;
        public int rotation; // <0 counter clockwise, >0 clockwise
        public int thrust;
        public Vector2 speed;
        public Vector2 position;

        public Ship(int fuel, int rotation, float x, float y) {
            this.fuel = fuel;
            this.rotation = 0;
            this.thrust = 0;
            this.speed = new Vector2(0, 0);
            this.position = new Vector2(x, y);
        }
    }

    public static class Surface {
        public Vector2[] points;

        public Surface(Vector2[] points) {
            this.points = points;
        }
    }
}
