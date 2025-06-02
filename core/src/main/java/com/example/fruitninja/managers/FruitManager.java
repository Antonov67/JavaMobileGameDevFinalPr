package com.example.fruitninja.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;
import com.example.fruitninja.GameSettings;
import com.example.fruitninja.objects.BladeObject;
import com.example.fruitninja.objects.BombObject;
import com.example.fruitninja.objects.FruitObject;
import com.example.fruitninja.objects.GameObject;
import com.example.fruitninja.objects.GameObjectType;

import java.util.ArrayList;
import java.util.Iterator;

public class FruitManager {
    private final World world;
    private final SpriteBatch batch;
    private final ArrayList<GameObject> gameObjects;
    private long lastFruitTime;
    private float spawnInterval = 1f;

    public FruitManager(World world, SpriteBatch batch) {
        this.world = world;
        this.batch = batch;
        this.gameObjects = new ArrayList<>();
        this.lastFruitTime = TimeUtils.nanoTime();
    }

    public void update(float delta) {
        // Спавн новых фруктов
        if (TimeUtils.nanoTime() - lastFruitTime > spawnInterval * 1000000000) {
            spawnRandomGameObject();
            lastFruitTime = TimeUtils.nanoTime();
        }

        // Удаление фруктов за пределами экрана
        Iterator<GameObject> iter = gameObjects.iterator();
        while (iter.hasNext()) {
            GameObject gameObject = iter.next();
            gameObject.update(delta);
            if (gameObject.isOutOfScreen()) {
                Gdx.app.log("Game", "gameObjects count on Screen: " + gameObjects.size());
                gameObject.dispose();
                iter.remove();
            }
        }
    }

    public void render() {
        for (GameObject gameObject : gameObjects) {
            gameObject.render(batch);
        }
    }

    public void checkSlice(BladeObject blade) {
        if (!blade.isSlicing()) return;

        Iterator<GameObject> iter = gameObjects.iterator();
        while (iter.hasNext()) {
            GameObject gameObject = iter.next();
            if (blade.intersects(gameObject.getPosition(), gameObject.getRadius())) {
                if (gameObject.isBomb()) {
                    // Взрыв бомбы
                    Gdx.app.log("Game", "Bomb hit! Game over!" + gameObject.getTexture().toString());
                } else {
                    // Разрезание фрукта
                    gameObject.slice();
                    Gdx.app.log("Game", "Fruit hit!" + gameObject.getTexture().toString());
                    // Здесь можно добавить очки и эффекты
                }
                iter.remove();
                gameObject.destroy(world);
            }
        }
    }

    private void spawnRandomGameObject() {
        //int x = (int) (Math.random() * GameSettings.SCREEN_WIDTH / GameSettings.SCALE);
        int x = (int) (Math.random() * GameSettings.SCREEN_WIDTH);
        int y = 50;

        // 5%  - это спаун бомб
        if (Math.random() < 0.2) {
            gameObjects.add(new BombObject(GameObjectType.BOMB, x, y, world, GameSettings.BOMB_BIT));
        } else {
            // выберем случайный фрукт
            GameObjectType[] types = GameObjectType.allExcept(GameObjectType.BOMB);
            GameObjectType type = types[(int) (Math.random() * types.length)];
            gameObjects.add(new FruitObject(type, x, y, world, GameSettings.FRUIT_BIT));
        }
    }

    public void dispose() {
        for (GameObject gameObject : gameObjects) {
            gameObject.dispose();
        }
        gameObjects.clear();
    }
}
