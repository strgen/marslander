package com.kolobaka.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

public class MainScreen implements Screen {

    List<Animation> shipAnimation;
    World world;
    WorldRender worldRender;
    private long startTime = 0;

    @Override
    public void show() {
        LoadShipAnimation();

        //init ship with coor
        //init surface
        //landX[ 0 ] = 0
        //landY[ 0 ] = 100
        //landX[ 1 ] = 1000
        //landY[ 1 ] = 500
        //landX[ 2 ] = 1500
        //landY[ 2 ] = 1500
        //landX[ 3 ] = 3000
        //landY[ 3 ] = 1000
        //landX[ 4 ] = 4000
        //landY[ 4 ] = 150
        //landX[ 5 ] = 5500
        //landY[ 5 ] = 150
        //landX[ 6 ] = 6999
        //landY[ 6 ] = 800
        //shipX = 2500
        //shipY = 2700
        Vector2[] points = new Vector2[7];

        points[0] = new Vector2(0f, 100f);
        points[1] = new Vector2(1000f, 500f);
        points[2] = new Vector2(1500f, 1500f);
        points[3] = new Vector2(3000f, 1000f);
        points[4] = new Vector2(4000f, 150f);
        points[5] = new Vector2(5500f, 150f);
        points[6] = new Vector2(6999f, 800f);
        /*points[0].x = 0f;
        points[0].y = 100f;*/
        /*points[1].x = 1000f;
        points[1].y = 500f;
        points[2].x = 1500f;
        points[2].y = 1500f;
        points[3].x = 3000f;
        points[3].y = 1000f;*/

        World.Surface surface = new World.Surface(points);
        World.Ship ship = new World.Ship(2000, 2500f, 2700f);
        world = new World(surface, ship);
        worldRender = new WorldRender(
                world,
                Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight(),
                shipAnimation,
                ship,
                surface);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        long timeSince = TimeUtils.timeSinceNanos(startTime);
        long timeMills = TimeUtils.nanosToMillis(timeSince);
        if(timeMills > 200) {
            world.iterate(4, -90);

            startTime = TimeUtils.nanoTime();
        }

        worldRender.render(delta);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
    private void LoadShipAnimation(){
        shipAnimation = new ArrayList<Animation>();

        shipAnimation.add(loadAnimation("marslander_sprite.png", 8, 1, 0.1f, new int[]{0, 1, 2, 3 , 4, 5, 6, 7}));
    }

    private Animation loadAnimation(  String path, int rows, int columns, float freq, int[] frames )
    {
        Texture texture;
        TextureRegion[] textureRegions;

        int amountFrames = rows;

        texture = new Texture(Gdx.files.internal(path));
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        TextureRegion[][] tmp = TextureRegion.split(texture, texture.getWidth() / amountFrames, texture.getHeight() / columns);
        TextureRegion[] customFrames = new TextureRegion[frames.length];
        textureRegions = new TextureRegion[ amountFrames * columns];
        int index = 0;
        for (int i = 0; i < columns; i++)
        {
            for (int j = 0; j < amountFrames; j++)
            {
                textureRegions[index++] = tmp[i][j];
            }
        }
        for( int indexC = 0; indexC < frames.length; indexC++ )
        {
            customFrames[ indexC ] = textureRegions[ frames[ indexC ] ];
        }

        return new Animation(freq, customFrames);
    }

}
