package com.example.fruitninja.managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.example.fruitninja.ui.BackgroundType;
import com.example.fruitninja.ui.BackgroundView;

public class BackgroundManager {

    private BackgroundView backgroundView;

    public BackgroundManager() {
        BackgroundType[] backgroundTypes = BackgroundType.values();
        BackgroundType type = backgroundTypes[(int) (Math.random() * backgroundTypes.length)];
        backgroundView = new BackgroundView(type.texturePath);
    }

    public void render(SpriteBatch batch){
        backgroundView.draw(batch);
    }
    public void dispose(){
        backgroundView.dispose();
    }
}
