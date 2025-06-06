package com.example.fruitninja.enums;

public enum GameObjectAfterBladeType {

    RED_APPLE_HALVES(
        GameObjectType.RED_APPLE,
        "halves/red_apple_half1.png",
        "halves/red_apple_half2.png",
        100),
    GREEN_APPLE_HALVES(
        GameObjectType.GREEN_APPLE,
        "halves/green_apple_half1.png",
        "halves/green_apple_half2.png",
        100),
    BANANA_HALVES(
        GameObjectType.BANANA,
        "halves/banana_half1.png",
            "halves/banana_half2.png",
            100),
    PINEAPPLE(
        GameObjectType.PINEAPPLE,
        "halves/pineapple_half1.png",
        "halves/pineapple_half2.png",
        100),
    ORANGE(
        GameObjectType.ORANGE,
        "halves/orange_half1.png",
        "halves/orange_half1.png",
        100),
    WATERMELON_HALVES(
        GameObjectType.WATERMELON,
        "halves/watermelon_half1.png",
            "halves/watermelon_half2.png",
            100),
    BOMB(
        GameObjectType.BOMB,
        "bombs/bomb_bang.png",
        "bombs/bomb_bang.png",
        100);

    public final GameObjectType gameObjectType;
    public final String texturePath1;
    public final String texturePath2;
    public final float radius;

    GameObjectAfterBladeType(GameObjectType gameObjectType, String texturePath1, String texturePath2, float radius) {
        this.gameObjectType = gameObjectType;
        this.texturePath1 = texturePath1;
        this.texturePath2 = texturePath2;
        this.radius = radius;
    }

   public static GameObjectAfterBladeType getGameObjectHalves(GameObjectType gameObjectType){
       for (GameObjectAfterBladeType g : values()) {
           if (g.gameObjectType.equals(gameObjectType))
               return g;
       }
       throw new IllegalArgumentException("No object");
   }
}
