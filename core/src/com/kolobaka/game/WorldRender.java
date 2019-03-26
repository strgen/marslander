package com.kolobaka.game;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class WorldRender {

    private static final float CAMERA_WIDTH = 7;
    private static final float CAMERA_HEIGHT = 3;

    public OrthographicCamera camera;

    private int widht;
    private int height;

    static double UPDATE_INTERVAL = 1.0f/60.0f;
    private World world;

    public WorldRender(World world, int camW, int camH) {
        this.world = world;
        this.camera = new OrthographicCamera(camW, camH);
        this.camera.position.set(camW / 2f, camH / 2f, 0);
        this.camera.update();
    }

    public void drawSurface(){

    }
}
