package com.example.fruitninja;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Blade {
    private Array<Vector2> points;
    private ShapeRenderer shapeRenderer;
    private boolean slicing = false;

    public Blade(SpriteBatch batch) {
        points = new Array<>();
        shapeRenderer = new ShapeRenderer();
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

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(new Color(1, 1, 1, 0.8f));

        for (int i = 0; i < points.size - 1; i++) {
            Vector2 p1 = points.get(i);
            Vector2 p2 = points.get(i + 1);
            shapeRenderer.line(p1.x, p1.y, p2.x, p2.y);
        }

        shapeRenderer.end();
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
        Vector2 lineDir = new Vector2(p2).sub(p1);
        Vector2 toCenter = new Vector2(center).sub(p1);

        float lineLength = lineDir.len();
        lineDir.nor();

        float projection = toCenter.dot(lineDir);
        projection = Math.max(0, Math.min(lineLength, projection));

        Vector2 closestPoint = new Vector2(p1).mulAdd(lineDir, projection);

        return closestPoint.dst(center) <= radius;
    }

    public boolean isSlicing() {
        return slicing;
    }

    public void dispose() {
        shapeRenderer.dispose();
    }
}
