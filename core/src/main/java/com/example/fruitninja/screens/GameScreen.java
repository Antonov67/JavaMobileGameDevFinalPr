package com.example.fruitninja.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;
import com.example.fruitninja.FruitNinjaGame;
import com.example.fruitninja.GameResources;
import com.example.fruitninja.GameSession;
import com.example.fruitninja.GameSettings;
import com.example.fruitninja.GameState;
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

    private FruitNinjaGame fruitNinjaGame;
    private GameObjectManager gameObjectManager;
    private BackgroundManager backgroundManager;
    private BladeObject blade;
    ImageView topBlackoutView;
    LiveView liveView;
    TextView scoreTextView;
    ButtonView pauseButton;
    ImageView fullBlackoutView;
    TextView pauseTextView;
    ButtonView homeButton;
    ButtonView continueButton;

    // ENDED state UI
    TextView recordsTextView;
    RecordsListView recordsListView;
    ButtonView homeButton2;

    GameSession gameSession;
    private int lives;


    public GameScreen(FruitNinjaGame fruitNinjaGame) {

        this.fruitNinjaGame = fruitNinjaGame;
        gameSession = new GameSession();

        gameObjectManager = new GameObjectManager(fruitNinjaGame.world, fruitNinjaGame.batch);

        blade = new BladeObject(fruitNinjaGame.batch);

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
            GameSettings.SCREEN_WIDTH / 2 - 350,
            GameSettings.SCREEN_HEIGHT / 2 - 500,
            GameResources.BLACKOUT_FULL_IMG_PATH);
        pauseTextView = new TextView(
            fruitNinjaGame.largeWhiteFont,
            GameSettings.SCREEN_WIDTH / 2 - 100,
            GameSettings.SCREEN_HEIGHT / 2 + 500,
            "Pause");
        homeButton = new ButtonView(
            GameSettings.SCREEN_WIDTH / 2 - 330,
            GameSettings.SCREEN_HEIGHT / 2 + 200,
            300, 100,
            fruitNinjaGame.commonBlackFont,
            GameResources.BUTTON_SHORT_BG_IMG_PATH,
            "Home"
        );
        continueButton = new ButtonView(
            GameSettings.SCREEN_WIDTH / 2 + 50,
            GameSettings.SCREEN_HEIGHT / 2 + 200,
            300, 100,
            fruitNinjaGame.commonBlackFont,
            GameResources.BUTTON_SHORT_BG_IMG_PATH,
            "Continue"
        );

        recordsListView = new RecordsListView(
            fruitNinjaGame.commonWhiteFont,
            GameSettings.SCREEN_HEIGHT / 2 + 400
        );
        recordsTextView = new TextView(
            fruitNinjaGame.largeWhiteFont,
            GameSettings.SCREEN_WIDTH / 2 - 200,
            GameSettings.SCREEN_HEIGHT / 2 + 500,
            "Last records"
        );
        homeButton2 = new ButtonView(
            GameSettings.SCREEN_WIDTH / 2 - 100,
            GameSettings.SCREEN_HEIGHT / 2,
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
        fruitNinjaGame.batch.begin();

        fruitNinjaGame.batch.setProjectionMatrix(fruitNinjaGame.camera.combined);
        backgroundManager.render(fruitNinjaGame.batch);

        if (gameSession.state == GameState.PLAYING) {

            // Рендеринг

            gameObjectManager.render();

            topBlackoutView.draw(fruitNinjaGame.batch);
            scoreTextView.draw(fruitNinjaGame.batch);
            liveView.draw(fruitNinjaGame.batch);
            pauseButton.draw(fruitNinjaGame.batch);

            scoreTextView.setText("Score: " + gameObjectManager.getFruitSliceCount());
            liveView.setLeftLives(lives - gameObjectManager.getBangCount());

            if ((lives - gameObjectManager.getBangCount()) == 0) {
                gameSession.setScore(gameObjectManager.getFruitSliceCount());
                gameSession.endGame();
                recordsListView.setRecords(MemoryManager.loadRecordsTable());
            }

        }

        if (gameSession.state == GameState.PAUSED) {

            fullBlackoutView.draw(fruitNinjaGame.batch);
            pauseTextView.draw(fruitNinjaGame.batch);
            homeButton.draw(fruitNinjaGame.batch);
            continueButton.draw(fruitNinjaGame.batch);

        } else if (gameSession.state == GameState.ENDED) {
            fullBlackoutView.draw(fruitNinjaGame.batch);
            recordsTextView.draw(fruitNinjaGame.batch);
            recordsListView.draw(fruitNinjaGame.batch);
            homeButton2.draw(fruitNinjaGame.batch);
        }

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
        if (fruitNinjaGame.audioManager.isSoundOn && blade.needToSound()) {
            fruitNinjaGame.audioManager.bladeSound.play(0.4f);
        }

        //звук бомбы
        if (fruitNinjaGame.audioManager.isSoundOn && gameObjectManager.needToBangSound()) {
            fruitNinjaGame.audioManager.bangSound.play(0.5f);
            gameObjectManager.setNeedToBangSound(false);
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
        lives = GameSettings.LIVES;
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

                case PAUSED:
                    if (continueButton.isHit(fruitNinjaGame.touch.x, fruitNinjaGame.touch.y)) {
                        gameSession.resumeGame();
                    }
                    if (homeButton.isHit(fruitNinjaGame.touch.x, fruitNinjaGame.touch.y)) {
                        fruitNinjaGame.setScreen(fruitNinjaGame.menuScreen);
                    }
                    break;

                case ENDED:

                    if (homeButton2.isHit(fruitNinjaGame.touch.x, fruitNinjaGame.touch.y)) {
                        fruitNinjaGame.setScreen(fruitNinjaGame.menuScreen);
                    }
                    break;
            }

        }
    }
}
