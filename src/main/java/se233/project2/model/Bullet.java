package se233.project2.model;

import javafx.application.Platform;

public class Bullet extends MovingObject {
    private boolean isDead;
    private int bulletLife;

    public Bullet(double x, double y, double vx, double vy, AnimatedSprite animatedSprite, double width, double height) {
        super(x, y, vx, vy, animatedSprite, width, height);
        isDead = false;
        bulletLife = 25;
        animatedSprite.setTimeline(30, 3);
    }

    @Override
    public void move() {
        super.move();
        bulletLife--;
        if (bulletLife <= 0) {
            die();
        }
    }

    @Override
    public void die() {
        isDead = true;
    }

    @Override
    public void draw() {
        Platform.runLater( () -> {
            if (animatedSprite.curIndex < 3)
                animatedSprite.start();
            this.setTranslateX(getX() - animatedSprite.getFitWidth() / 2);
            this.setTranslateY(getY() - animatedSprite.getFitHeight() / 2);
        });
    }

    public boolean isDead() {
        return isDead;
    }

    public void setBulletLife(int life) {
        this.bulletLife = life;
    }

}