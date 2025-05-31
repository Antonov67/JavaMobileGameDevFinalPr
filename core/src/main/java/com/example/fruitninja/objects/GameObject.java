package com.example.fruitninja.objects;

import static com.example.fruitninja.GameSettings.SCALE;
import static com.example.fruitninja.GameSettings.maxAngularVelocity;
import static com.example.fruitninja.GameSettings.minAngularVelocity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.example.fruitninja.GameSettings;

public class GameObject {
    protected final Body body;
    protected int width;
    protected int height;
    protected Texture texture;
    protected float radius;
    protected Sprite sprite;
    protected boolean isBomb;

    public short cBits;

    GameObject(FruitType type, int x, int y, World world, short cBits) {

        this.radius = type.radius;
        this.width = (int) (radius * 2);
        this.height = (int) (radius * 2);
        this.cBits = cBits;

        // Создание спрайта
        sprite = new Sprite(new Texture(type.texturePath));
        sprite.setSize(radius * 2, radius * 2);
        sprite.setOrigin(radius, radius);

        texture = new Texture(type.texturePath);
        body = createBody(x, y, world);
    }


    public void draw(SpriteBatch batch) {
        batch.draw(texture, getX() - (width / 2f), getY() - (height / 2f), width, height);
    }

    public boolean isBomb() {
        return isBomb;
    }


    private Body createBody(float x, float y, World world) {
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.fixedRotation = false;
        Body body = world.createBody(def);

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(Math.max(width, height) * SCALE / 2f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.density = 0.1f;
        fixtureDef.friction = 1f;
        fixtureDef.restitution = 0.3f;
        fixtureDef.filter.categoryBits = cBits;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
        circleShape.dispose();

        // Применение случайного импульса
        float impulseX = (float) (Math.random() * 500 + 200);
        float impulseY = (float) (Math.random() * 1000 + 400);

        if (x > GameSettings.SCREEN_WIDTH / 2 / SCALE) {
            impulseX = -impulseX;
        }
        body.applyLinearImpulse(new Vector2(impulseX, impulseY), body.getWorldCenter(), true);

        //случайное направление и скорость вращения фрукта
        body.setAngularVelocity(MathUtils.random(minAngularVelocity, maxAngularVelocity));


        body.setTransform(x * SCALE, y * SCALE, 0);
        return body;
    }

    public void update(float delta) {
        // Обновление позиции спрайта по позиции тела
        Vector2 position = body.getPosition();
        sprite.setPosition(position.x - radius, position.y - radius);
        sprite.setRotation((float) Math.toDegrees(body.getAngle()));
    }

    public void hit() {
    }

    public void render(SpriteBatch batch) {

    }

    public boolean isOutOfScreen() {
        return getX() < -radius || getX() > GameSettings.SCREEN_WIDTH / SCALE || getY() < -radius;
    }

    public int getX() {
        return (int) (body.getPosition().x / SCALE);
    }

    public int getY() {
        return (int) (body.getPosition().y / SCALE);
    }

    public void setX(int x) {
        body.setTransform(x * SCALE, body.getPosition().y, 0);
    }

    public void setY(int y) {
        body.setTransform(body.getPosition().x, y * SCALE, 0);
    }

    public void dispose() {
        texture.dispose();
    }
}
