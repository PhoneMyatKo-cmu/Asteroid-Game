package se233.project2.model;

public class Boss  extends Character{

    public Boss(double x, double y, double vx, double vy, AnimatedSprite animatedSprite, int hp, double width, double height) {
        super(x, y, vx, vy, animatedSprite, hp, width, height);
    }

    @Override
    public void die() {

    }
}
