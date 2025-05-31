package com.example.fruitninja.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.example.fruitninja.FruitNinjaGame;
import com.example.fruitninja.managers.BackgroundManager;
import com.example.fruitninja.managers.FruitManager;

public class GameScreen extends ScreenAdapter {

    private FruitNinjaGame fruitNinjaGame;
    private FruitManager fruitManager;
    private BackgroundManager backgroundManager;


    public GameScreen(FruitNinjaGame fruitNinjaGame) {

        this.fruitNinjaGame = fruitNinjaGame;
        fruitManager = new FruitManager(fruitNinjaGame.world, fruitNinjaGame.batch);
        backgroundManager = new BackgroundManager();
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
        fruitManager.render(delta);
      //  blade.render();
        fruitNinjaGame.batch.end();



    }

    private void update(float delta) {
        // Обновление физики
        fruitNinjaGame.stepWorld();

        // Обновление фруктов и проверка столкновений
        fruitManager.update(delta);
       // fruitManager.checkSlice(blade);

        // Обновление камеры
        fruitNinjaGame.camera.update();
    }

    @Override
    public void dispose() {
        backgroundManager.dispose();
        fruitManager.dispose();
    }
}
