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
import com.badlogic.gdx.math.MathUtils;
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

    private static float shipW = 128;
    private static float shipH = 256;

    private static float shipWR = shipW / 8;
    private static float shipHR = shipH / 8;

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

    private Vector2 converCoor(Vector2 vector2){
        return new Vector2( (vector2.x / WW) * Gdx.graphics.getWidth(),
                (vector2.y / WH) * Gdx.graphics.getHeight());
    }
    public void render(float dt)
    {
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderShip();
        renderSurface();
    }

    private void renderShip(){

        TextureRegion texture = (TextureRegion)shipAnimation.get(0).getKeyFrame(0, true);
        Sprite shipSprite = new Sprite(texture);
        shipSprite.setSize(shipWR, shipHR);
        shipSprite.setOrigin(shipWR / 2, 0 );

        Vector2 vector2 = converCoor(new Vector2(ship.position.x, ship.position.y));

        shipSprite.setPosition(vector2.x - shipSprite.getOriginX(),
                vector2.y- shipSprite.getOriginY() );

        //because of fuck you, just joking there is some trick with starting coor system iam pretty sure you'll get
        shipSprite.setRotation(-ship.rotation);

        spriteBatch.begin();
        shipSprite.draw(spriteBatch);
        spriteBatch.end();

        /*
        spriteBatch.begin();
        spriteBatch.draw(shipSprite, vector2.x - (shipWR / 2), vector2.y, shipWR, shipHR);
        spriteBatch.end();
        */
    }

    private void renderSurface(){
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        //World.Point[] points = surface.point;

        for (int i = 1; i < surface.points.length; i++) {
            Vector2 lineStart = new Vector2(surface.points[i - 1].x, surface.points[i - 1].y);
            Vector2 lineEnd = new Vector2(surface.points[i].x, surface.points[i].y);
            shapeRenderer.line(converCoor(lineStart), converCoor(lineEnd));
        }

        shapeRenderer.end();
    }
}
