package com.example.fruitninja.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

public class BombObject extends GameObject {


    public BombObject(GameObjectType type, int x, int y, World world, short cBits) {
        super(type, x, y, world, cBits);
        this.isBomb = true;
    }

    public void render(SpriteBatch batch) {
        if (!sliced) {
            sprite.draw(batch);
        }
    }

    public void slice() {
        if (!sliced) {
            sliced = true;
            // Здесь можно добавить эффект взрыва
        }
    }
}
