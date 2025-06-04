package com.example.fruitninja.objects;

import com.badlogic.gdx.physics.box2d.World;

public class BombObject extends GameObject {


    public BombObject(GameObjectType type, int x, int y, World world, short cBits) {
        super(type, x, y, world, cBits);
        this.isBomb = true;
    }

    public BombObject(String texturePath, float radius, int x, int y, World world, short cBits, boolean sliced) {
        super(texturePath, radius, x, y, world, cBits, sliced);
        this.isBomb = true;
        this.sliced = sliced;
    }
}
