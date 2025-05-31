package com.example.fruitninja;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;

public class InputHandler extends InputAdapter {
    private final Blade blade;

    public InputHandler(Blade blade) {
        this.blade = blade;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        blade.startSlice(screenX, screenY);
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        blade.addPoint(screenX, screenY);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        blade.endSlice();
        return true;
    }
}
