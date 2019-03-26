package com.kolobaka.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import sun.java2d.ScreenUpdateManager;

public class MarsLanderGame extends Game {

	@Override
	public void create () {
		ScreenManager.getInstance().init(this);
		ScreenManager.getInstance().show(Screen.MAIN);
	}

	@Override
	public void dispose () {
		super.dispose();
		ScreenManager.getInstance().dispose();
	}
}
