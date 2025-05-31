package com.example.fruitninja;

import static com.example.fruitninja.GameSettings.POSITION_ITERATIONS;
import static com.example.fruitninja.GameSettings.STEP_TIME;
import static com.example.fruitninja.GameSettings.VELOCITY_ITERATIONS;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.example.fruitninja.screens.GameScreen;

public class FruitNinjaGame extends Game {
    public SpriteBatch batch;
    public World world;
    public OrthographicCamera camera;
    float accumulator = 0;

    @Override
    public void create() {
        Box2D.init();
        world = new World(new Vector2(0, -9.8f), true);

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT);

        setScreen(new GameScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public void stepWorld() {
        float delta = Gdx.graphics.getDeltaTime();
        accumulator += delta;

        if (accumulator >= STEP_TIME) {
            accumulator -= STEP_TIME;
            world.step(STEP_TIME, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
        }
    }
}
