package com.example.fruitninja.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

public class FruitObject extends GameObject {

    public FruitObject(GameObjectType type, int x, int y, World world, short cBits) {
        super(type, x, y, world, cBits);
        this.isBomb = false;
    }


    public void render(SpriteBatch batch) {
        if (!sliced) {
            sprite.draw(batch);
        }
    }

    public void slice() {
        if (!sliced) {
            sliced = true;
            // Здесь можно добавить эффект разрезания
            // и создать два половинки фрукта
        }
    }
}
