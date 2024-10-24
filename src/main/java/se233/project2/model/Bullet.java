package se233.project2.model;

public class Bullet extends MovingObject {
    private boolean isDead;
    private int bulletLife;

    public Bullet(double x, double y, double vx, double vy, AnimatedSprite animatedSprite, double width, double height) {
        super(x, y, vx, vy, animatedSprite, width, height);
        isDead = false;
        bulletLife = 50;
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

    public boolean isDead() {
        return isDead;
    }

}