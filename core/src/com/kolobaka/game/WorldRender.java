package com.kolobaka.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

public class WorldRender {

    private static final float CAMERA_WIDTH = 7;
    private static final float CAMERA_HEIGHT = 3;

    private static final float WW = 7000;
    private static final float WH = 3000;

    private final SpriteBatch spriteBatch;

    public OrthographicCamera camera;

    private int widht;
    private int height;

    static double UPDATE_INTERVAL = 1.0f/60.0f;

    private World world;
    private World.Ship ship;
    private World.Surface surface;

    private static float shipW = 130 / 8;
    private static float shipH = 255 / 8;

    List<Animation> shipAnimation;

    public WorldRender(World world,
                       int camW,
                       int camH,
                       List<Animation> shipAnimation,
                       World.Ship ship,
                       World.Surface surface) {
        this.world = world;
        this.camera = new OrthographicCamera(camW, camH);
        this.camera.position.set(camW / 2f, camH / 2f, 0);
        this.camera.update();
        this.shipAnimation = shipAnimation;
        this.ship = ship;
        this.surface = surface;

        spriteBatch = new SpriteBatch();

    }

    public void drawSurface(){

    }

    private Vector2 converCoor(float x, float y){
        return new Vector2( (x / WW) * Gdx.graphics.getWidth(), (y / WH) * Gdx.graphics.getHeight());
    }
    public void render(float dt)
    {
        //Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderShip();
    }

    private void renderShip(){
        spriteBatch.begin();
        TextureRegion texture = (TextureRegion)shipAnimation.get(0).getKeyFrame(0, true);
        Sprite shipSprite = new Sprite(texture);

        Vector2 vector2 = converCoor(ship.position.x, ship.position.y);
        spriteBatch.draw(shipSprite, vector2.x - (shipW / 2), vector2.y, shipW, shipH);
        spriteBatch.end();
    }

    /*private void renderSurface(){
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        World.Point[] points = surface.point;


        shapeRenderer.line();

        shapeRenderer.end();
    }*/
}
