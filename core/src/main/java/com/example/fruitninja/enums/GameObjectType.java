package com.example.fruitninja.enums;

import java.util.Arrays;

public enum GameObjectType {
    RED_APPLE("fruits/red_apple.png", 100),
    GREEN_APPLE("fruits/green_apple.png", 100),
    BANANA("fruits/banana.png", 100),
    ORANGE("fruits/orange.png", 100),
    WATERMELON("fruits/watermelon.png", 100),
    PINEAPPLE("fruits/pineapple.png", 100),
    BOMB("bombs/bomb.png", 100);

    public final String texturePath;
    public final float radius;

    GameObjectType(String texturePath, int radius) {
        this.texturePath = texturePath;
        this.radius = radius;
    }

    //выберем только фрукты
    public static GameObjectType[] allExcept(GameObjectType excluded) {
        return Arrays.stream(values())
            .filter(f -> f != excluded)
            .toArray(GameObjectType[]::new);
    }
}
