package com.example.fruitninja.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

public class FruitObject extends GameObject {

    public FruitObject(GameObjectType type, int x, int y, World world, short cBits) {
        super(type, x, y, world, cBits);
        this.isBomb = false;
    }

    public FruitObject(String texturePath, float radius, int x, int y, World world, short cBits) {
        super(texturePath, radius, x, y, world, cBits);
        this.isBomb = false;
    }

}
