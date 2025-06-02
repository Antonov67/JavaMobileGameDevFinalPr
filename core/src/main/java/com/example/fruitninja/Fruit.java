package com.example.fruitninja;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.example.fruitninja.objects.GameObjectType;

public class Fruit {
    protected Sprite sprite;
    protected Body body;
    protected float radius;
    protected boolean sliced = false;
    protected boolean isBomb = false;
    private World world;

    public Fruit(World world, GameObjectType type, float x, float y) {
        this.radius = type.radius;
        this.world = world;

        // Создание спрайта
        sprite = new Sprite(new Texture(type.texturePath));
        sprite.setSize(radius * 2, radius * 2);
        sprite.setOrigin(radius, radius);

        // Создание физического тела
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        body = world.createBody(bodyDef);

        CircleShape circle = new CircleShape();
        circle.setRadius(radius);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.3f;

        body.createFixture(fixtureDef);
        circle.dispose();

        // Применение случайного импульса
        float impulseX = (float) (Math.random() * 5 - 2.5);
        float impulseY = (float) (Math.random() * 10 + 5);
        body.applyLinearImpulse(new Vector2(impulseX, impulseY), body.getWorldCenter(), true);
    }

    public void update(float delta) {
        // Обновление позиции спрайта по позиции тела
        Vector2 position = body.getPosition();
        sprite.setPosition(position.x - radius, position.y - radius);
        sprite.setRotation((float) Math.toDegrees(body.getAngle()));
    }

    public void render(SpriteBatch batch) {
        if (!sliced) {
            sprite.draw(batch);
        }
    }


    public boolean isOutOfScreen() {
        return body.getPosition().y < -50 || body.getPosition().x < -50 || body.getPosition().x > 850;
    }

    public void slice() {
        if (!sliced) {
            sliced = true;
            // Здесь можно добавить эффект разрезания
            // и создать два половинки фрукта
        }
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }

    public float getRadius() {
        return radius;
    }

    public boolean isBomb() {
        return isBomb;
    }

    public void dispose() {
        sprite.getTexture().dispose();
        world.destroyBody(body);
    }
}
