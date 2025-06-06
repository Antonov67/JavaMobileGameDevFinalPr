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

    private final FruitNinjaGame fruitNinjaGame;

    private final BackgroundManager backgroundManager;
    private final TextView titleTextView;
    private final ImageView blackoutImageView;
    private final ButtonView returnButton;
    private final TextView musicSettingView;
    private final TextView soundSettingView;
    private final TextView clearSettingView;

    public SettingsScreen(FruitNinjaGame fruitNinjaGame) {
        this.fruitNinjaGame = fruitNinjaGame;

        backgroundManager = new BackgroundManager();
        titleTextView = new TextView(fruitNinjaGame.largeWhiteFont,
            (float) GameSettings.SCREEN_WIDTH / 2 - 110,
            (float) GameSettings.SCREEN_HEIGHT / 2 + 500,
            "Settings");
        blackoutImageView = new ImageView(
            (float) GameSettings.SCREEN_WIDTH / 2 - 250,
            (float) GameSettings.SCREEN_HEIGHT / 2 - 200,
            GameResources.BLACKOUT_MIDDLE_IMG_PATH);
        clearSettingView = new TextView(fruitNinjaGame.commonWhiteFont,
            (float) GameSettings.SCREEN_WIDTH / 2 - 130,
            (float) GameSettings.SCREEN_HEIGHT / 2,
            "clear records");

        musicSettingView = new TextView(
            fruitNinjaGame.commonWhiteFont,
            (float) GameSettings.SCREEN_WIDTH / 2 - 100,
            (float) GameSettings.SCREEN_HEIGHT / 2 + 200,
            "music: " + translateStateToText(MemoryManager.loadIsMusicOn())
        );

        soundSettingView = new TextView(
            fruitNinjaGame.commonWhiteFont,
            (float) GameSettings.SCREEN_WIDTH / 2 - 100,
            (float) GameSettings.SCREEN_HEIGHT / 2 + 100,
            "sound: " + translateStateToText(MemoryManager.loadIsSoundOn())
        );

        returnButton = new ButtonView(
            (float) GameSettings.SCREEN_WIDTH / 2 - 100,
            (float) GameSettings.SCREEN_HEIGHT / 2 - 150,
            260, 100,
            fruitNinjaGame.commonBlackFont,
            GameResources.BUTTON_SHORT_BG_IMG_PATH,
            "return"
        );

    }

    @Override
    public void render(float delta) {

        handleInput();

        fruitNinjaGame.getCamera().update();
        fruitNinjaGame.getBatch().setProjectionMatrix(fruitNinjaGame.getCamera().combined);
        ScreenUtils.clear(Color.CLEAR);

        fruitNinjaGame.getBatch().begin();
        backgroundManager.render(fruitNinjaGame.getBatch());
        titleTextView.draw(fruitNinjaGame.getBatch());
        blackoutImageView.draw(fruitNinjaGame.getBatch());
        returnButton.draw(fruitNinjaGame.getBatch());
        musicSettingView.draw(fruitNinjaGame.getBatch());
        soundSettingView.draw(fruitNinjaGame.getBatch());
        clearSettingView.draw(fruitNinjaGame.getBatch());

        fruitNinjaGame.getBatch().end();
    }

    void handleInput() {
        if (Gdx.input.justTouched()) {
            fruitNinjaGame.setTouch(fruitNinjaGame.getCamera().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)));

            if (returnButton.isHit(fruitNinjaGame.getTouch().x, fruitNinjaGame.getTouch().y)) {
                fruitNinjaGame.setScreen(fruitNinjaGame.getMenuScreen());
            }
            if (clearSettingView.isHit(fruitNinjaGame.getTouch().x, fruitNinjaGame.getTouch().y)) {
                MemoryManager.saveTableOfRecords(new ArrayList<>());
                clearSettingView.setText("clear records (cleared)");
            }
            if (musicSettingView.isHit(fruitNinjaGame.getTouch().x, fruitNinjaGame.getTouch().y)) {
                MemoryManager.saveMusicSettings(!MemoryManager.loadIsMusicOn());
                musicSettingView.setText("music: " + translateStateToText(MemoryManager.loadIsMusicOn()));
                fruitNinjaGame.getAudioManager().updateMusicFlag();
            }
            if (soundSettingView.isHit(fruitNinjaGame.getTouch().x, fruitNinjaGame.getTouch().y)) {
                MemoryManager.saveSoundSettings(!MemoryManager.loadIsSoundOn());
                soundSettingView.setText("sound: " + translateStateToText(MemoryManager.loadIsSoundOn()));
                fruitNinjaGame.getAudioManager().updateSoundFlag();
            }
        }
    }

    private String translateStateToText(boolean state) {
        return state ? "ON" : "OFF";
    }
}
