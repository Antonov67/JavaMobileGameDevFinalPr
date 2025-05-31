package com.example.fruitninja;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.example.fruitninja.objects.BladeObject;

public class InputHandler extends InputAdapter {
    private final BladeObject blade;

    public InputHandler(BladeObject blade) {
        this.blade = blade;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        blade.startSlice(screenX, Gdx.graphics.getHeight() - screenY);
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        blade.addPoint(screenX, Gdx.graphics.getHeight() - screenY);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        blade.endSlice();
        return true;
    }
}
