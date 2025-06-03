package com.example.fruitninja.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.example.fruitninja.FruitNinjaGame;
import com.example.fruitninja.InputHandler;
import com.example.fruitninja.managers.BackgroundManager;
import com.example.fruitninja.managers.GameObjectManager;
import com.example.fruitninja.objects.BladeObject;

public class GameScreen extends ScreenAdapter {

    private FruitNinjaGame fruitNinjaGame;
    private GameObjectManager gameObjectManager;
    private BackgroundManager backgroundManager;
    private BladeObject blade;



    public GameScreen(FruitNinjaGame fruitNinjaGame) {

        this.fruitNinjaGame = fruitNinjaGame;

        gameObjectManager = new GameObjectManager(fruitNinjaGame.world, fruitNinjaGame.batch);

        blade = new BladeObject(fruitNinjaGame.batch);

        backgroundManager = new BackgroundManager();

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
