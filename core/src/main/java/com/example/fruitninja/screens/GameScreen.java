package com.example.fruitninja.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;
import com.example.fruitninja.FruitNinjaGame;
import com.example.fruitninja.GameResources;
import com.example.fruitninja.GameSession;
import com.example.fruitninja.GameSettings;
import com.example.fruitninja.enums.GameState;
import com.example.fruitninja.InputHandler;
import com.example.fruitninja.managers.BackgroundManager;
import com.example.fruitninja.managers.GameObjectManager;
import com.example.fruitninja.managers.MemoryManager;
import com.example.fruitninja.objects.BladeObject;
import com.example.fruitninja.ui.ButtonView;
import com.example.fruitninja.ui.ImageView;
import com.example.fruitninja.ui.LiveView;
import com.example.fruitninja.ui.RecordsListView;
import com.example.fruitninja.ui.TextView;

public class GameScreen extends ScreenAdapter {

    private final FruitNinjaGame fruitNinjaGame;
    private GameObjectManager gameObjectManager;
    private final BackgroundManager backgroundManager;
    private final BladeObject blade;
    private final ImageView topBlackoutView;
    private final LiveView liveView;
    private final TextView scoreTextView;
    private final ButtonView pauseButton;
    private final ImageView fullBlackoutView;
    private final TextView pauseTextView;
    private final ButtonView homeButton;
    private final ButtonView continueButton;
    private final TextView recordsTextView;
    private final RecordsListView recordsListView;
    private final ButtonView homeButton2;
    private final GameSession gameSession;
    private int lives;


    public GameScreen(FruitNinjaGame fruitNinjaGame) {

        this.fruitNinjaGame = fruitNinjaGame;
        gameSession = new GameSession();

        gameObjectManager = new GameObjectManager(fruitNinjaGame.getWorld(), fruitNinjaGame.getBatch());

        blade = new BladeObject(fruitNinjaGame.getBatch());

        lives = GameSettings.LIVES;

        backgroundManager = new BackgroundManager();
        topBlackoutView = new ImageView(
            0,
            GameSettings.SCREEN_HEIGHT - 150,
            GameSettings.SCREEN_WIDTH,
            130,
            GameResources.BLACKOUT_TOP_IMG_PATH);
        liveView = new LiveView(
            GameSettings.SCREEN_WIDTH - 500,
            GameSettings.SCREEN_HEIGHT - 130
        );
        scoreTextView = new TextView(
            fruitNinjaGame.commonWhiteFont,
            30,
            GameSettings.SCREEN_HEIGHT - 100,
            "0"
        );
        pauseButton = new ButtonView(
            GameSettings.SCREEN_WIDTH - 150,
            GameSettings.SCREEN_HEIGHT - 120,
            46, 54,
            GameResources.PAUSE_IMG_PATH
        );

        fullBlackoutView = new ImageView(
            (float) GameSettings.SCREEN_WIDTH / 2 - 350,
            (float) GameSettings.SCREEN_HEIGHT / 2 - 500,
            GameResources.BLACKOUT_FULL_IMG_PATH);
        pauseTextView = new TextView(
            fruitNinjaGame.largeWhiteFont,
            (float) GameSettings.SCREEN_WIDTH / 2 - 100,
            (float) GameSettings.SCREEN_HEIGHT / 2 + 500,
            "Pause");
        homeButton = new ButtonView(
            (float) GameSettings.SCREEN_WIDTH / 2 - 330,
            (float) GameSettings.SCREEN_HEIGHT / 2 + 200,
            300, 100,
            fruitNinjaGame.commonBlackFont,
            GameResources.BUTTON_SHORT_BG_IMG_PATH,
            "Home"
        );
        continueButton = new ButtonView(
            (float) GameSettings.SCREEN_WIDTH / 2 + 50,
            (float) GameSettings.SCREEN_HEIGHT / 2 + 200,
            300, 100,
            fruitNinjaGame.commonBlackFont,
            GameResources.BUTTON_SHORT_BG_IMG_PATH,
            "Continue"
        );

        recordsListView = new RecordsListView(
            fruitNinjaGame.commonWhiteFont,
            (float) GameSettings.SCREEN_HEIGHT / 2 + 400
        );
        recordsTextView = new TextView(
            fruitNinjaGame.largeWhiteFont,
            (float) GameSettings.SCREEN_WIDTH / 2 - 200,
            (float) GameSettings.SCREEN_HEIGHT / 2 + 500,
            "Last records"
        );
        homeButton2 = new ButtonView(
            (float) GameSettings.SCREEN_WIDTH / 2 - 100,
            (float) GameSettings.SCREEN_HEIGHT / 2,
            200, 100,
            fruitNinjaGame.commonBlackFont,
            GameResources.BUTTON_SHORT_BG_IMG_PATH,
            "Home"
        );

        // Настройка обработки ввода
        Gdx.input.setInputProcessor(new InputHandler(blade));
    }

    @Override
    public void render(float delta) {

        fruitNinjaGame.stepWorld();

        // Очистка экрана
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Обновление игрового мира
        update(delta);
        fruitNinjaGame.getBatch().begin();

        fruitNinjaGame.getBatch().setProjectionMatrix(fruitNinjaGame.getCamera().combined);
        backgroundManager.render(fruitNinjaGame.getBatch());

        if (gameSession.getState() == GameState.PLAYING) {

            // Рендеринг

            gameObjectManager.render();

            topBlackoutView.draw(fruitNinjaGame.getBatch());
            scoreTextView.draw(fruitNinjaGame.getBatch());
            liveView.draw(fruitNinjaGame.getBatch());
            pauseButton.draw(fruitNinjaGame.getBatch());

            scoreTextView.setText("Score: " + gameObjectManager.getFruitSliceCount());
            liveView.setLeftLives(lives - gameObjectManager.getBangCount());

            if ((lives - gameObjectManager.getBangCount()) == 0) {
                gameSession.setScore(gameObjectManager.getFruitSliceCount());
                gameSession.endGame();
                recordsListView.setRecords(MemoryManager.loadRecordsTable());
            }
        }

        if (gameSession.getState() == GameState.PAUSED) {

            fullBlackoutView.draw(fruitNinjaGame.getBatch());
            pauseTextView.draw(fruitNinjaGame.getBatch());
            homeButton.draw(fruitNinjaGame.getBatch());
            continueButton.draw(fruitNinjaGame.getBatch());

        } else if (gameSession.getState() == GameState.ENDED) {
            fullBlackoutView.draw(fruitNinjaGame.getBatch());
            recordsTextView.draw(fruitNinjaGame.getBatch());
            recordsListView.draw(fruitNinjaGame.getBatch());
            homeButton2.draw(fruitNinjaGame.getBatch());
        }

        fruitNinjaGame.getBatch().end();
        //отрисовка лезвия после завершения отрисовки фона и фруктов
        blade.render();
    }

    private void update(float delta) {
        // Обновление физики
        fruitNinjaGame.stepWorld();

        handleInput();

        // Обновление фруктов и проверка разрезаний
        gameObjectManager.update(delta);
        gameObjectManager.checkSlice(blade);

        //звук лезвия
        if (fruitNinjaGame.getAudioManager().isSoundOn && blade.needToSound()) {
            fruitNinjaGame.getAudioManager().bladeSound.play(0.4f);
        }

        //звук бомбы
        if (fruitNinjaGame.getAudioManager().isSoundOn && gameObjectManager.needToBangSound()) {
            fruitNinjaGame.getAudioManager().bangSound.play(0.5f);
            gameObjectManager.setNeedToBangSound(false);
        }

        // Обновление камеры
        fruitNinjaGame.getCamera().update();
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
        gameObjectManager = new GameObjectManager(fruitNinjaGame.getWorld(), fruitNinjaGame.getBatch());
        lives = GameSettings.LIVES;
        gameSession.startGame();
    }

    private void handleInput() {
        if (Gdx.input.isTouched()) {
            fruitNinjaGame.setTouch(fruitNinjaGame.getCamera().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)));

            switch (gameSession.getState()) {
                case PLAYING:
                    if (pauseButton.isHit(fruitNinjaGame.getTouch().x, fruitNinjaGame.getTouch().y)) {
                        gameSession.pauseGame();
                    }

                    break;

                case PAUSED:
                    if (continueButton.isHit(fruitNinjaGame.getTouch().x, fruitNinjaGame.getTouch().y)) {
                        gameSession.resumeGame();
                    }
                    if (homeButton.isHit(fruitNinjaGame.getTouch().x, fruitNinjaGame.getTouch().y)) {
                        fruitNinjaGame.setScreen(fruitNinjaGame.getMenuScreen());
                    }
                    break;

                case ENDED:

                    if (homeButton2.isHit(fruitNinjaGame.getTouch().x, fruitNinjaGame.getTouch().y)) {
                        fruitNinjaGame.setScreen(fruitNinjaGame.getMenuScreen());
                    }
                    break;
            }
        }
    }
}
