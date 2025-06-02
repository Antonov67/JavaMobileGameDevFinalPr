package com.example.fruitninja.objects;

import java.util.Arrays;

public enum GameObjectType {
    RED_APPLE("fruits/red_apple.png", 55),
    GREEN_APPLE("fruits/green_apple.png", 55),
    BANANA("fruits/banana.png", 60),
    ORANGE("fruits/orange.png", 58),
    WATERMELON("fruits/watermelon.png", 65),
    PINEAPPLE("fruits/pineapple.png", 55),
    BOMB("bombs/bomb.png", 60);

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
