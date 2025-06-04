package com.example.fruitninja.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;
import com.example.fruitninja.FruitNinjaGame;
import com.example.fruitninja.GameResources;
import com.example.fruitninja.GameSession;
import com.example.fruitninja.GameSettings;
import com.example.fruitninja.InputHandler;
import com.example.fruitninja.managers.BackgroundManager;
import com.example.fruitninja.managers.GameObjectManager;
import com.example.fruitninja.objects.BladeObject;
import com.example.fruitninja.ui.ButtonView;
import com.example.fruitninja.ui.ImageView;
import com.example.fruitninja.ui.LiveView;
import com.example.fruitninja.ui.TextView;

public class GameScreen extends ScreenAdapter {

    private FruitNinjaGame fruitNinjaGame;
    private GameObjectManager gameObjectManager;
    private BackgroundManager backgroundManager;
    private BladeObject blade;
    ImageView topBlackoutView;
    LiveView liveView;
    TextView scoreTextView;
    ButtonView pauseButton;

    GameSession gameSession;



    public GameScreen(FruitNinjaGame fruitNinjaGame) {

        this.fruitNinjaGame = fruitNinjaGame;
        gameSession = new GameSession();

        gameObjectManager = new GameObjectManager(fruitNinjaGame.world, fruitNinjaGame.batch);

        blade = new BladeObject(fruitNinjaGame.batch);

        backgroundManager = new BackgroundManager();
        topBlackoutView = new ImageView(
            0,
            GameSettings.SCREEN_HEIGHT - 150,
            GameSettings.SCREEN_WIDTH,
            130,
            GameResources.BLACKOUT_TOP_IMG_PATH);
        liveView = new LiveView(
            GameSettings.SCREEN_WIDTH  - 500,
            GameSettings.SCREEN_HEIGHT - 130
        );
        scoreTextView = new TextView(fruitNinjaGame.commonWhiteFont, 50, 1215);
        pauseButton = new ButtonView(
            GameSettings.SCREEN_WIDTH  - 150,
            GameSettings.SCREEN_HEIGHT - 120,
            46, 54,
            GameResources.PAUSE_IMG_PATH
        );

        // Настройка обработки ввода
        //Gdx.input.setInputProcessor(new InputHandler(blade));
    }

    @Override
    public void render(float delta) {

        fruitNinjaGame.stepWorld();

        // Очистка экрана
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Обновление игрового мира
        update(delta);

        // Рендеринг
        fruitNinjaGame.batch.setProjectionMatrix(fruitNinjaGame.camera.combined);

        fruitNinjaGame.batch.begin();

        backgroundManager.render(fruitNinjaGame.batch);

        gameObjectManager.render();

        topBlackoutView.draw(fruitNinjaGame.batch);
        //scoreTextView.draw(fruitNinjaGame.batch);
        liveView.draw(fruitNinjaGame.batch);
        pauseButton.draw(fruitNinjaGame.batch);

        fruitNinjaGame.batch.end();
        //отрисовка лезвия после завершения отрисовки фона и фруктов
        blade.render();
    }

    private void update(float delta) {
        // Обновление физики
        fruitNinjaGame.stepWorld();

        handleInput();

        // Обновление фруктов и проверка столкновений
        gameObjectManager.update(delta);
        gameObjectManager.checkSlice(blade);

        //звук лезвия
        if (fruitNinjaGame.audioManager.isSoundOn && blade.needToSound()){
            fruitNinjaGame.audioManager.bladeSound.play(0.4f);
        }

        // Обновление камеры
        fruitNinjaGame.camera.update();
    }

    @Override
    public void dispose() {
        fruitNinjaGame.dispose();
        backgroundManager.dispose();
        gameObjectManager.dispose();
        blade.dispose();
    }

    @Override
    public void show() {
        restartGame();
    }

    private void restartGame() {


        gameObjectManager.dispose();
        gameObjectManager = new GameObjectManager(fruitNinjaGame.world, fruitNinjaGame.batch);
        gameSession.startGame();
    }

    private void handleInput() {
        if (Gdx.input.isTouched()) {
            fruitNinjaGame.touch = fruitNinjaGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

            switch (gameSession.state) {
                case PLAYING:
                    if (pauseButton.isHit(fruitNinjaGame.touch.x, fruitNinjaGame.touch.y)) {
                        gameSession.pauseGame();
                    }

                    break;

//                case PAUSED:
//                    if (continueButton.isHit(fruitNinjaGame.touch.x, fruitNinjaGame.touch.y)) {
//                        gameSession.resumeGame();
//                    }
//                    if (homeButton.isHit(fruitNinjaGame.touch.x, fruitNinjaGame.touch.y)) {
//                        fruitNinjaGame.setScreen(fruitNinjaGame.menuScreen);
//                    }
//                    break;
//
//                case ENDED:
//
//                    if (homeButton2.isHit(fruitNinjaGame.touch.x, fruitNinjaGame.touch.y)) {
//                        fruitNinjaGame.setScreen(fruitNinjaGame.menuScreen);
//                    }
//                    break;
            }

        }
    }
}
