package com.example.fruitninja;

import static com.example.fruitninja.GameSettings.POSITION_ITERATIONS;
import static com.example.fruitninja.GameSettings.STEP_TIME;
import static com.example.fruitninja.GameSettings.VELOCITY_ITERATIONS;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.example.fruitninja.managers.AudioManager;
import com.example.fruitninja.screens.GameScreen;
import com.example.fruitninja.screens.MenuScreen;
import com.example.fruitninja.screens.SettingsScreen;

public class FruitNinjaGame extends Game {
    private SpriteBatch batch;
    private World world;
    public BitmapFont largeWhiteFont;
    public BitmapFont commonWhiteFont;
    public BitmapFont commonBlackFont;
    private OrthographicCamera camera;
    private float accumulator = 0;
    private GameScreen gameScreen;
    private MenuScreen menuScreen;
    private SettingsScreen settingsScreen;
    private Vector3 touch;
    private AudioManager audioManager;

    @Override
    public void create() {
        Box2D.init();
        world = new World(new Vector2(0, -9.8f), true);

        largeWhiteFont = FontBuilder.generate(70, Color.WHITE, GameResources.GAME_FONT);
        commonWhiteFont = FontBuilder.generate(45, Color.WHITE, GameResources.GAME_FONT);
        commonBlackFont = FontBuilder.generate(50, Color.BLACK, GameResources.GAME_FONT);

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT);

        gameScreen = new GameScreen(this);
        menuScreen = new MenuScreen(this);
        settingsScreen = new SettingsScreen(this);

        audioManager = new AudioManager();

        setScreen(menuScreen);

        touch = new Vector3();
    }

    @Override
    public void render() {
        super.render();
    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }

    public MenuScreen getMenuScreen() {
        return menuScreen;
    }

    public SettingsScreen getSettingsScreen() {
        return settingsScreen;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public Vector3 getTouch() {
        return touch;
    }

    public void setTouch(Vector3 touch) {
        this.touch = touch;
    }

    public AudioManager getAudioManager() {
        return audioManager;
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
