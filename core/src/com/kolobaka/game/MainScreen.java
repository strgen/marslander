package com.kolobaka.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.List;

public class MainScreen implements Screen {

    List<Animation> shipAnimation;
    World world;
    WorldRender worldRender;

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
        //world = World();
        //WorldRender = new WorldRender();

    }

    @Override
    public void render(float delta) {

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
