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
import com.example.fruitninja.managers.MemoryManager;
import com.example.fruitninja.ui.ButtonView;
import com.example.fruitninja.ui.ImageView;
import com.example.fruitninja.ui.TextView;

import java.util.ArrayList;

public class SettingsScreen extends ScreenAdapter {

    FruitNinjaGame fruitNinjaGame;

    private BackgroundManager backgroundManager;
    TextView titleTextView;
    ImageView blackoutImageView;
    ButtonView returnButton;
    TextView musicSettingView;
    TextView soundSettingView;
    TextView clearSettingView;

    public SettingsScreen(FruitNinjaGame fruitNinjaGame) {
        this.fruitNinjaGame = fruitNinjaGame;

        backgroundManager = new BackgroundManager();
        titleTextView = new TextView(fruitNinjaGame.largeWhiteFont,
            GameSettings.SCREEN_WIDTH / 2 - 110,
            GameSettings.SCREEN_HEIGHT / 2 + 500,
            "Settings");
        blackoutImageView = new ImageView(
            GameSettings.SCREEN_WIDTH / 2 - 250,
            GameSettings.SCREEN_HEIGHT / 2 - 200,
            GameResources.BLACKOUT_MIDDLE_IMG_PATH);
        clearSettingView = new TextView(fruitNinjaGame.commonWhiteFont,
            GameSettings.SCREEN_WIDTH / 2 - 130,
            GameSettings.SCREEN_HEIGHT / 2,
            "clear records");

        musicSettingView = new TextView(
            fruitNinjaGame.commonWhiteFont,
            GameSettings.SCREEN_WIDTH / 2 - 100,
            GameSettings.SCREEN_HEIGHT / 2 + 200,
            "music: " + translateStateToText(MemoryManager.loadIsMusicOn())
        );

        soundSettingView = new TextView(
            fruitNinjaGame.commonWhiteFont,
            GameSettings.SCREEN_WIDTH / 2 - 100,
            GameSettings.SCREEN_HEIGHT / 2 + 100,
            "sound: " + translateStateToText(MemoryManager.loadIsSoundOn())
        );

        returnButton = new ButtonView(
            GameSettings.SCREEN_WIDTH / 2 - 100,
            GameSettings.SCREEN_HEIGHT / 2 - 150,
            260, 100,
            fruitNinjaGame.commonBlackFont,
            GameResources.BUTTON_SHORT_BG_IMG_PATH,
            "return"
        );

    }

    @Override
    public void render(float delta) {

        handleInput();

        fruitNinjaGame.camera.update();
        fruitNinjaGame.batch.setProjectionMatrix(fruitNinjaGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        fruitNinjaGame.batch.begin();
        backgroundManager.render(fruitNinjaGame.batch);
        titleTextView.draw(fruitNinjaGame.batch);
        blackoutImageView.draw(fruitNinjaGame.batch);
        returnButton.draw(fruitNinjaGame.batch);
        musicSettingView.draw(fruitNinjaGame.batch);
        soundSettingView.draw(fruitNinjaGame.batch);
        clearSettingView.draw(fruitNinjaGame.batch);

        fruitNinjaGame.batch.end();
    }

    void handleInput() {
        if (Gdx.input.justTouched()) {
            fruitNinjaGame.touch = fruitNinjaGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (returnButton.isHit(fruitNinjaGame.touch.x, fruitNinjaGame.touch.y)) {
                fruitNinjaGame.setScreen(fruitNinjaGame.menuScreen);
            }
            if (clearSettingView.isHit(fruitNinjaGame.touch.x, fruitNinjaGame.touch.y)) {
                MemoryManager.saveTableOfRecords(new ArrayList<>());
                clearSettingView.setText("clear records (cleared)");
            }
            if (musicSettingView.isHit(fruitNinjaGame.touch.x, fruitNinjaGame.touch.y)) {
                MemoryManager.saveMusicSettings(!MemoryManager.loadIsMusicOn());
                musicSettingView.setText("music: " + translateStateToText(MemoryManager.loadIsMusicOn()));
                fruitNinjaGame.audioManager.updateMusicFlag();
            }
            if (soundSettingView.isHit(fruitNinjaGame.touch.x, fruitNinjaGame.touch.y)) {
                MemoryManager.saveSoundSettings(!MemoryManager.loadIsSoundOn());
                soundSettingView.setText("sound: " + translateStateToText(MemoryManager.loadIsSoundOn()));
                fruitNinjaGame.audioManager.updateSoundFlag();
            }
        }
    }

    private String translateStateToText(boolean state) {
        return state ? "ON" : "OFF";
    }
}
