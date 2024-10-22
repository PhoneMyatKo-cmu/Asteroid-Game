package se233.project2.model;

public class Asteroid extends Character {
    private int level;

    public Asteroid(double x, double y, double ax, double ay, AnimatedSprite animatedSprite, int hp, int level) {
        super(x, y, ax, ay, animatedSprite, hp, 60 * level, 60 * level);
    }
}
