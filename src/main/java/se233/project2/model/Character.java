package se233.project2.model;

public abstract class Character extends MovingObject {
    private int hp; // hit point

    public Character(double x, double y, double ax, double ay, AnimatedSprite animatedSprite, int hp, double width, double height) {
        super(x, y, ax, ay, animatedSprite, width, height);
        this.hp = hp;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}
