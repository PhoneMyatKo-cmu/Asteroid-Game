package se233.project2.model;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import se233.project2.Launcher;
import se233.project2.controller.DeathRenderTask;

import java.util.ArrayList;

public class PlayerShip extends Character {
    private KeyCode moveUpKey = KeyCode.W;
    private KeyCode moveLeftKey = KeyCode.A;
    private KeyCode moveDownKey = KeyCode.S;
    private KeyCode moveRightKey = KeyCode.D;
    private KeyCode turnLeftKey = KeyCode.LEFT;
    private KeyCode turnRightKey = KeyCode.RIGHT;
    private KeyCode shootKey = KeyCode.SPACE;
    private boolean isDead;
    private final int bulletSpeed = 3;
    private ArrayList<Bullet> bulletList;

    public PlayerShip(double x, double y, AnimatedSprite animatedSprite, int hp, double width, double height) {
        super(x, y, 0, 0, animatedSprite, hp, width, height);
        isDead = false;
        bulletList = new ArrayList<>();
    }

    public void moveRight() { setAx(0.3); }
    public void moveLeft() {
        setAx(-0.3);
    }
    public void moveUp() { setAy(-0.3); }
    public void moveDown() { setAy(0.3); }

    public void turnLeft() {
        Platform.runLater(() -> animatedSprite.setRotate(animatedSprite.getRotate() - 5));
    }
    public void turnRight() {
        Platform.runLater(() -> animatedSprite.setRotate(animatedSprite.getRotate() + 5));
    }
    public void die() {
        this.setX(350);
        this.setY(500);
        this.setHp(getHp() - 1);

        DeathRenderTask task = new DeathRenderTask(this);
        (new Thread(task)).start();
    }

    public void shoot() {
        double bvx = Math.sin(Math.toRadians(this.getRotate())) * bulletSpeed;
        double bvy = -Math.cos(Math.toRadians(this.getRotate())) * bulletSpeed;

        Bullet bullet = new Bullet(this.getBoundsInParent().getCenterX(), this.getBoundsInParent().getCenterY(), 0, 0, new AnimatedSprite(new Image(Launcher.class.getResource("laserRed14.png").toString()), 1, 1, 1, 0, 0, 13, 57), 13, 57);
        bullet.setVx(bvx);
        bullet.setVy(bvy);
        Platform.runLater(() -> bullet.setRotate(animatedSprite.getRotate()));
        bulletList.add(bullet);
        Platform.runLater(() -> ((Pane) getParent()).getChildren().add(bullet));
    }

    @Override
    public boolean isCollided(MovingObject movingObject) {
        if (isDead) return false;
        return super.isCollided(movingObject);
    }


    public void setDead(boolean disabled) { this.isDead = disabled; }

    public boolean isDead() { return isDead; }






    public KeyCode getMoveUpKey() {
        return moveUpKey;
    }

    public KeyCode getMoveLeftKey() {
        return moveLeftKey;
    }

    public KeyCode getMoveDownKey() {
        return moveDownKey;
    }

    public KeyCode getMoveRightKey() {
        return moveRightKey;
    }

    public ArrayList<Bullet> getBulletList() {
        return bulletList;
    }

    public KeyCode getTurnLeftKey() {
        return turnLeftKey;
    }

    public KeyCode getTurnRightKey() {
        return turnRightKey;
    }

    public KeyCode getShootKey() {
        return shootKey;
    }
}
