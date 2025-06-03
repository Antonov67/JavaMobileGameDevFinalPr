package com.example.fruitninja.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.example.fruitninja.FruitNinjaGame;
import com.example.fruitninja.GameResources;
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



    public GameScreen(FruitNinjaGame fruitNinjaGame) {

        this.fruitNinjaGame = fruitNinjaGame;

        gameObjectManager = new GameObjectManager(fruitNinjaGame.world, fruitNinjaGame.batch);

        blade = new BladeObject(fruitNinjaGame.batch);

        backgroundManager = new BackgroundManager();
        topBlackoutView = new ImageView(0, 1180, GameResources.BLACKOUT_TOP_IMG_PATH);
        liveView = new LiveView(305, 1215);
        scoreTextView = new TextView(fruitNinjaGame.commonWhiteFont, 50, 1215);
        pauseButton = new ButtonView(
            605, 1200,
            46, 54,
            GameResources.PAUSE_IMG_PATH
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

        // Рендеринг
        fruitNinjaGame.batch.setProjectionMatrix(fruitNinjaGame.camera.combined);

        fruitNinjaGame.batch.begin();

        backgroundManager.render(fruitNinjaGame.batch);

        gameObjectManager.render();

        fruitNinjaGame.batch.end();
        //отрисовка лезвия после завершения отрисовки фона и фруктов
        blade.render();
    }

    private void update(float delta) {
        // Обновление физики
        fruitNinjaGame.stepWorld();

        // Обновление фруктов и проверка столкновений
        gameObjectManager.update(delta);
        gameObjectManager.checkSlice(blade);

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
}
