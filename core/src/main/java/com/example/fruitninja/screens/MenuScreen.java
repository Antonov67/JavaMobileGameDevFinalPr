package com.example.fruitninja.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.example.fruitninja.FruitNinjaGame;
import com.example.fruitninja.GameResources;
import com.example.fruitninja.GameSettings;
import com.example.fruitninja.managers.BackgroundManager;
import com.example.fruitninja.ui.ButtonView;
import com.example.fruitninja.ui.TextView;

public class MenuScreen extends ScreenAdapter {

    private final FruitNinjaGame fruitNinjaGame;

    private final BackgroundManager backgroundManager;
    private final TextView titleView;
    private final ButtonView startButtonView;
    private final ButtonView settingsButtonView;
    private final ButtonView exitButtonView;

    public MenuScreen(FruitNinjaGame fruitNinjaGame) {
        this.fruitNinjaGame = fruitNinjaGame;

        backgroundManager = new BackgroundManager();
        titleView = new TextView(
            fruitNinjaGame.largeWhiteFont,
            (float) GameSettings.SCREEN_WIDTH / 2 - 340,
            (float) GameSettings.SCREEN_HEIGHT / 2 + 500,
            "Fruit Ninja Game");
        startButtonView = new ButtonView(
            (float) GameSettings.SCREEN_WIDTH / 2 - 220,
            (float) GameSettings.SCREEN_HEIGHT / 2 + 200, 440, 100,
            fruitNinjaGame.commonBlackFont,
            GameResources.BUTTON_LONG_BG_IMG_PATH, "start");
        settingsButtonView = new ButtonView(
            (float) GameSettings.SCREEN_WIDTH / 2 - 220,
            (float) GameSettings.SCREEN_HEIGHT / 2 + 50, 440, 100,
            fruitNinjaGame.commonBlackFont,
            GameResources.BUTTON_LONG_BG_IMG_PATH, "settings");
        exitButtonView = new ButtonView(
            (float) GameSettings.SCREEN_WIDTH / 2 - 220,
            (float) GameSettings.SCREEN_HEIGHT / 2 - 100, 440, 100,
            fruitNinjaGame.commonBlackFont,
            GameResources.BUTTON_LONG_BG_IMG_PATH, "exit");
    }

    @Override
    public void render(float delta) {

        handleInput();

        fruitNinjaGame.getCamera().update();
        fruitNinjaGame.getBatch().setProjectionMatrix(fruitNinjaGame.getCamera().combined);
        ScreenUtils.clear(Color.CLEAR);

        fruitNinjaGame.getBatch().begin();

        backgroundManager.render(fruitNinjaGame.getBatch());
        titleView.draw(fruitNinjaGame.getBatch());
        exitButtonView.draw(fruitNinjaGame.getBatch());
        settingsButtonView.draw(fruitNinjaGame.getBatch());
        startButtonView.draw(fruitNinjaGame.getBatch());

        fruitNinjaGame.getBatch().end();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            fruitNinjaGame.setTouch(fruitNinjaGame.getCamera().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)));

            if (startButtonView.isHit(fruitNinjaGame.getTouch().x, fruitNinjaGame.getTouch().y)) {
                fruitNinjaGame.setScreen(fruitNinjaGame.getGameScreen());
            }
            if (exitButtonView.isHit(fruitNinjaGame.getTouch().x, fruitNinjaGame.getTouch().y)) {
                Gdx.app.exit();
            }
            if (settingsButtonView.isHit(fruitNinjaGame.getTouch().x,
                fruitNinjaGame.getTouch().y)) {
                fruitNinjaGame.setScreen(fruitNinjaGame.getSettingsScreen());
            }
        }
    }
}
