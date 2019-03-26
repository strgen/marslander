package com.kolobaka.game;

public enum Screen {
    MAIN {
        @Override
        protected com.badlogic.gdx.Screen getScreenInstance() {
            return new MainScreen();
        }
    };

    protected abstract com.badlogic.gdx.Screen getScreenInstance();
}
