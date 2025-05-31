package com.example.fruitninja.objects;

public enum FruitType {
    RED_APPLE("fruits/red_apple.png", 55),
    GREEN_APPLE("fruits/green_apple.png", 55),
    BANANA("fruits/banana.png", 60),
    ORANGE("fruits/orange.png", 58),
    WATERMELON("fruits/watermelon.png", 65),
    PINEAPPLE("fruits/pineapple.png", 55),
    BOMB("bombs/bomb.png", 60);

    public final String texturePath;
    public final float radius;

    FruitType(String texturePath, int radius) {
        this.texturePath = texturePath;
        this.radius = radius;
    }
}
