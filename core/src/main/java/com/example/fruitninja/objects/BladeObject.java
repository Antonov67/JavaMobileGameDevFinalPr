package com.example.fruitninja.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class BladeObject {
    private Array<Vector2> points;
    private ShapeRenderer shapeRenderer;
    private boolean slicing = false;


    public BladeObject(SpriteBatch batch) {
        points = new Array<>();
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
    }

    public void startSlice(float x, float y) {
        points.clear();
        points.add(new Vector2(x, y));
        slicing = true;
    }

    public void addPoint(float x, float y) {
        if (slicing) {
            points.add(new Vector2(x, y));
        }
    }

    public void endSlice() {
        slicing = false;
    }

    public void render() {
        if (points.size < 2) return;

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

//        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//        shapeRenderer.setColor(new Color(1, 1, 1, 0.8f));
//
//        for (int i = 0; i < points.size - 1; i++) {
//            Vector2 p1 = points.get(i);
//            Vector2 p2 = points.get(i + 1);
//            shapeRenderer.line(p1.x, p1.y, p2.x, p2.y);
//        }
//
//        shapeRenderer.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(1, 1, 1, 0.8f));
        // Рисуем толстую линию как прямоугольник
        float thickness = 10f; // Толщина линии
        for (int i = 0; i < points.size - 1; i++) {
            Vector2 p1 = points.get(i);
            Vector2 p2 = points.get(i + 1);
            float angle = MathUtils.atan2(p2.y - p1.y, p2.x - p1.x);
            float length = Vector2.dst(p1.x, p1.y, p2.x, p2.y);
            shapeRenderer.rect(p1.x, p1.y - thickness / 2, 0, thickness / 2,
                length, thickness, 1, 1, angle * MathUtils.radiansToDegrees);
        }
        shapeRenderer.end();

        if (!slicing){
            points.clear();
        }

        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    public boolean intersects(Vector2 center, float radius) {
        if (points.size < 2) return false;

        for (int i = 0; i < points.size - 1; i++) {
            Vector2 p1 = points.get(i);
            Vector2 p2 = points.get(i + 1);

            // Простая проверка пересечения линии и круга
            if (intersectsLineCircle(p1, p2, center, radius)) {
                return true;
            }
        }

        return false;
    }

    private boolean intersectsLineCircle(Vector2 p1, Vector2 p2, Vector2 center, float radius) {
        Vector2 d = new Vector2(p2).sub(p1);
        Vector2 f = new Vector2(center).sub(p1);

        // Квадрат длины отрезка
        float a = d.dot(d);
        // Проекция вектора f на d (умноженная на 2)
        float b = 2 * f.dot(d);
        // Квадрат длины вектора f минус квадрат радиуса
        float c = f.dot(f) - radius * radius;

        // Дискриминант квадратного уравнения
        float discriminant = b * b - 4 * a * c;

        // Если дискриминант отрицательный - нет пересечений
        if (discriminant < 0) {
            return false;
        }

        // Иначе ищем точки пересечения
        discriminant = (float) Math.sqrt(discriminant);

        // Решения квадратного уравнения
        float t1 = (-b - discriminant) / (2 * a);
        float t2 = (-b + discriminant) / (2 * a);

        // Если хотя бы одно решение в диапазоне [0,1] - есть пересечение с отрезком
        return (t1 >= 0 && t1 <= 1) || (t2 >= 0 && t2 <= 1);
    }

    public boolean isSlicing() {
        return slicing;
    }

    public void dispose() {
        shapeRenderer.dispose();
    }
}
