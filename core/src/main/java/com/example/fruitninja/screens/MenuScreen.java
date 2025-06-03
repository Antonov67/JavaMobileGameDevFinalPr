package com.example.fruitninja.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.example.fruitninja.FruitNinjaGame;
import com.example.fruitninja.GameResources;
import com.example.fruitninja.managers.BackgroundManager;
import com.example.fruitninja.ui.BackgroundView;
import com.example.fruitninja.ui.ButtonView;
import com.example.fruitninja.ui.TextView;

public class MenuScreen extends ScreenAdapter {

    FruitNinjaGame fruitNinjaGame;

    BackgroundManager backgroundManager;
    TextView titleView;
    ButtonView startButtonView;
    ButtonView settingsButtonView;
    ButtonView exitButtonView;

    public MenuScreen(FruitNinjaGame fruitNinjaGame) {
        this.fruitNinjaGame = fruitNinjaGame;

        backgroundManager = new BackgroundManager();
        titleView = new TextView(fruitNinjaGame.largeWhiteFont, 180, 960, "Fruit Ninja Game");
        startButtonView = new ButtonView(140, 646, 440, 70, fruitNinjaGame.commonBlackFont, GameResources.BUTTON_LONG_BG_IMG_PATH, "start");
        settingsButtonView = new ButtonView(140, 551, 440, 70, fruitNinjaGame.commonBlackFont, GameResources.BUTTON_LONG_BG_IMG_PATH, "settings");
        exitButtonView = new ButtonView(140, 456, 440, 70, fruitNinjaGame.commonBlackFont, GameResources.BUTTON_LONG_BG_IMG_PATH, "exit");
    }

    @Override
    public void render(float delta) {

        handleInput();

        fruitNinjaGame.camera.update();
        fruitNinjaGame.batch.setProjectionMatrix(fruitNinjaGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        fruitNinjaGame.batch.begin();

        backgroundManager.render(fruitNinjaGame.batch);
        titleView.draw(fruitNinjaGame.batch);
        exitButtonView.draw(fruitNinjaGame.batch);
        settingsButtonView.draw(fruitNinjaGame.batch);
        startButtonView.draw(fruitNinjaGame.batch);

        fruitNinjaGame.batch.end();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            fruitNinjaGame.touch = fruitNinjaGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (startButtonView.isHit(fruitNinjaGame.touch.x, fruitNinjaGame.touch.y)) {
                fruitNinjaGame.setScreen(fruitNinjaGame.gameScreen);
            }
            if (exitButtonView.isHit(fruitNinjaGame.touch.x, fruitNinjaGame.touch.y)) {
                Gdx.app.exit();
            }
            if (settingsButtonView.isHit(fruitNinjaGame.touch.x,
                fruitNinjaGame.touch.y)) {
                //fruitNinjaGame.setScreen(fruitNinjaGame.settingsScreen);
            }
        }
    }
}
