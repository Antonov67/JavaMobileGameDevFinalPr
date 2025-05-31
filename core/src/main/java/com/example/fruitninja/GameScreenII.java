package com.example.fruitninja;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.example.fruitninja.managers.FruitManager;
import com.example.fruitninja.objects.BladeObject;

public class GameScreenII implements Screen {
    private final FruitNinjaGame game;
    private OrthographicCamera camera;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private FruitManager fruitManager;
    private BladeObject blade;

    public GameScreenII(FruitNinjaGame game) {
        this.game = game;

        // Настройка камеры
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        // Создание физического мира с гравитацией
        world = new World(new Vector2(0, -9.8f), true);
        debugRenderer = new Box2DDebugRenderer();

        // Инициализация менеджера фруктов и лезвия
        fruitManager = new FruitManager(world, game.batch);
        blade = new BladeObject(game.batch);

        // Настройка обработки ввода
        Gdx.input.setInputProcessor(new InputHandler(blade));
    }

    @Override
    public void render(float delta) {
        // Очистка экрана
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Обновление игрового мира
        update(delta);

        // Рендеринг
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        fruitManager.render();
        blade.render();
        game.batch.end();

        // Отладка физики (можно отключить в релизе)
        debugRenderer.render(world, camera.combined);
    }

    private void update(float delta) {
        // Обновление физики
        world.step(1/60f, 6, 2);

        // Обновление фруктов и проверка столкновений
        fruitManager.update(delta);
        fruitManager.checkSlice(blade);

        // Обновление камеры
        camera.update();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

    @Override
    public void dispose() {
        world.dispose();
        debugRenderer.dispose();
        fruitManager.dispose();
    }

    // Остальные методы интерфейса Screen...
    @Override public void show() {}
    @Override public void hide() {}
    @Override public void pause() {}
    @Override public void resume() {}
}
