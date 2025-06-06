package com.example.fruitninja;

import com.badlogic.gdx.Gdx;

public class GameSettings {

    public static float minAngularVelocity = -3f; // Минимальная скорость (вращение против часовой)
    public static float maxAngularVelocity = 3f;  // Максимальная скорость (вращение по часовой)
    public static final int SCREEN_WIDTH = Gdx.graphics.getWidth();
    public static final int SCREEN_HEIGHT = Gdx.graphics.getHeight();
    public static final float STEP_TIME = 1f / 60;
    public static final int VELOCITY_ITERATIONS = 6;
    public static final int POSITION_ITERATIONS = 3;
    public static final int LIFE_TIME = 100;
    public static final int LIVES = 3;
}
