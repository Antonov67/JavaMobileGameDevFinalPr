package com.example.fruitninja.objects;

import com.badlogic.gdx.physics.box2d.World;
import com.example.fruitninja.enums.GameObjectType;

public class FruitObject extends GameObject {

    public FruitObject(GameObjectType type, int x, int y, World world) {
        super(type, x, y, world);
        this.isBomb = false;
    }

    public FruitObject(String texturePath, float radius, int x, int y, World world, boolean sliced) {
        super(texturePath, radius, x, y, world, sliced);
        this.isBomb = false;
        this.sliced = sliced;
    }

}
