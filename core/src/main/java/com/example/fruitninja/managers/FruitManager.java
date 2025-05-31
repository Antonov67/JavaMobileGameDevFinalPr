package com.example.fruitninja.managers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;
import com.example.fruitninja.Blade;
import com.example.fruitninja.GameSettings;
import com.example.fruitninja.objects.BombObject;
import com.example.fruitninja.objects.FruitObject;
import com.example.fruitninja.objects.FruitType;
import com.example.fruitninja.objects.GameObject;

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
            GameObject fruit = iter.next();
            fruit.update(delta);
            if (fruit.isOutOfScreen()) {
                System.out.println("count" + gameObjects.size());
                fruit.dispose();
                iter.remove();
            }
        }
    }

    public void render(float delta) {
        for (GameObject gameObject : gameObjects) {
            gameObject.render(batch);
        }
    }

    public void checkSlice(Blade blade) {
//        if (!blade.isSlicing()) return;
//
//        Iterator<FruitObject> iter = fruits.iterator();
//        while (iter.hasNext()) {
//            FruitObject fruit = iter.next();
//            if (blade.intersects(fruit.getPosition(), fruit.getRadius())) {
//                if (fruit.isBomb()) {
//                    // Взрыв бомбы
//                    Gdx.app.log("Game", "Bomb hit! Game over!");
//                } else {
//                    // Разрезание фрукта
//                    fruit.slice();
//                    // Здесь можно добавить очки и эффекты
//                }
//            }
//        }
    }

    private void spawnRandomGameObject() {
        int x = (int) (Math.random() * GameSettings.SCREEN_WIDTH / GameSettings.SCALE);
        int y = -50;

        // 20% chance to spawn a bomb
        if (Math.random() < 0.2) {
            gameObjects.add(new BombObject(FruitType.BOMB, x, y, world, GameSettings.BOMB_BIT));
        } else {
            // Random fruit type
            FruitType[] types = FruitType.values();
            FruitType type = types[(int) (Math.random() * types.length)];
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
