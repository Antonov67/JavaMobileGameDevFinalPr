package com.example.fruitninja.objects;

import com.badlogic.gdx.physics.box2d.World;
import com.example.fruitninja.Fruit;

public class BombObject extends GameObject {

    public BombObject(FruitType type, int x, int y, World world, short cBits) {
        super(type, x, y, world, cBits);
        this.isBomb = true;
    }
}
