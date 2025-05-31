package com.example.fruitninja.ui;

public enum BackgroundType {

    BG_1("textures/bg1.png"),
    BG_2("textures/bg2.png"),
    BG_3("textures/bg3.png"),
    BG_4("textures/bg4.png");

    public final String texturePath;

    BackgroundType(String texturePath) {
        this.texturePath = texturePath;
    }
}
