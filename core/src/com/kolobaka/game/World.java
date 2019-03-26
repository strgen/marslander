package com.kolobaka.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

public class World {
    public final Point G = new Point(0, 3.711f); // Meters per sec squared
    public final static int HEIGHT = 3000;
    public final static int WIDHT = 7000;

    private Surface surface;
    private Ship ship;

    private long startTime = 0;

    public World( Surface surface, Ship ship) {
        this.surface = surface;
        this.ship = ship;
    }

    public void itterate(){
        //sum GVector + AVector + AEngineVector
        //get new point
        //wait sec

        long timeSince = TimeUtils.timeSinceNanos(startTime);
        long timeMills = TimeUtils.nanosToMillis(timeSince);
        if(timeMills > 1000) {
            ship.a.x = G.x + ship.a.x;
            ship.a.y = G.y + ship.a.y;

            startTime = TimeUtils.nanoTime();
        }


    }

    // Vector G { x: 0, y: 3.711 }
    // Points in the world
    public class Ship extends PObject{
        int fuel;

        public Ship(int fuel, Vector2 y) {
            this.fuel = fuel;
        }
    }

    public class Surface extends PObject{
        Point[] point;
        public Surface(Point[] point) {
            this.point = point;
            this.isStatic = true;
        }
    }

    public class PObject{
        Vector2 a;
        float vV;
        float hV;

        public PObject() {
        }

        public boolean isStatic() {
            return isStatic;
        }

        public void setStatic(boolean aStatic) {
            isStatic = aStatic;
        }

        boolean isStatic = false;

        public PObject(Vector2 a, float vSpeed, float hSpeed){
            this.a = a;
            this.vV = vSpeed;
            this.hV = hSpeed;
        }
    }

    public class Point{
        float x;
        float y;

        public void setX(float x) {
            this.x = x;
        }

        public void setY(float y) {
            this.y = y;
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }
}
