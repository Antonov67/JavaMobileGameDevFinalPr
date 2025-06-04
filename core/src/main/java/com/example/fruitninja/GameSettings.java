package com.example.fruitninja;

import com.badlogic.gdx.Gdx;

public class GameSettings {

    public static final float SCALE = 0.05f;
    public static float minAngularVelocity = -3f; // Минимальная скорость (вращение против часовой)
    public static float maxAngularVelocity = 3f;  // Максимальная скорость (вращение по часовой)

    public static final int SCREEN_WIDTH = Gdx.graphics.getWidth();
    public static final int SCREEN_HEIGHT = Gdx.graphics.getHeight();

    public static final float STEP_TIME = 1f / 60;
    public static final int VELOCITY_ITERATIONS = 6;
    public static final int POSITION_ITERATIONS = 3;
    public static final short FRUIT_BIT = 1;
    public static final short BOMB_BIT = 2;
    public static final int STARTING_TRASH_APPEARANCE_COOL_DOWN = 2000;
    public static final int LIFE_TIME = 100;
}
