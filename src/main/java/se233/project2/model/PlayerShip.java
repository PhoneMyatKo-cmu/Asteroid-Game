package se233.project2.model;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import se233.project2.Launcher;
import se233.project2.controller.RespawnTask;
import se233.project2.view.GameStage;

import java.util.ArrayList;

public class PlayerShip extends Character {
    private KeyCode moveUpKey = KeyCode.W;
    private KeyCode moveLeftKey = KeyCode.A;
    private KeyCode moveDownKey = KeyCode.S;
    private KeyCode moveRightKey = KeyCode.D;
    private KeyCode turnLeftKey = KeyCode.LEFT;
    private KeyCode turnRightKey = KeyCode.RIGHT;
    private KeyCode shootKey = KeyCode.SPACE;
    private boolean isDead, isActive;
    private final int bulletSpeed = 20;
    private ArrayList<Bullet> bulletList;
    private long lastShotTime = 0;
    private int fireRate = 3;
    private int score;

    public PlayerShip(double x, double y, AnimatedSprite animatedSprite, int hp, double width, double height) {
        super(x, y, 0, 0, animatedSprite, hp, width, height);
        isDead = false;
        isActive = true;
        bulletList = new ArrayList<>();
        score = 0;
    }

    public void moveRight() { setAx(1); }
    public void moveLeft() {
        setAx(-1);
    }
    public void moveUp() { setAy(-1); }
    public void moveDown() { setAy(1); }

    public void turnLeft() {
        Platform.runLater(() -> animatedSprite.setRotate(animatedSprite.getRotate() - 5));
    }
    public void turnRight() {
        Platform.runLater(() -> animatedSprite.setRotate(animatedSprite.getRotate() + 5));
    }
    public void die() {
        isDead = true;
        this.setHp(getHp() - 1);
        if (this.getHp() == 0)
            ((GameStage) this.getParent()).setRunning(false);
    }

    public void shoot() {
        if (System.currentTimeMillis() - lastShotTime < 1000/fireRate || isDead()) {
            return;
        }
        double bvx = Math.sin(Math.toRadians(animatedSprite.getRotate())) * bulletSpeed;
        double bvy = -Math.cos(Math.toRadians(animatedSprite.getRotate())) * bulletSpeed;
        double bx = Math.sin(Math.toRadians(animatedSprite.getRotate())) * animatedSprite.getFitHeight()/2 + getX();
        double by = -Math.cos(Math.toRadians(animatedSprite.getRotate())) * animatedSprite.getFitHeight()/2 + getY();

        Bullet bullet = new Bullet(bx, by, 0, 0, new AnimatedSprite(new Image(Launcher.class.getResource("laserRed14.png").toString()), 1, 1, 1, 0, 0, 13, 57), 13, 20);
        bullet.setVx(bvx);
        bullet.setVy(bvy);
        Platform.runLater(() -> bullet.animatedSprite.setRotate(animatedSprite.getRotate()));
        bulletList.add(bullet);
        Platform.runLater(() -> ((Pane) getParent()).getChildren().add(bullet));
        lastShotTime = System.currentTimeMillis();
    }

    public void deathRender() {
        Platform.runLater(() -> {
            this.setVisible(false);
            Explosion explosion = new Explosion((Pane) this.getParent(), this.getX(), this.getY());
//            playerShip.setActive(false);
        });
    }

    public void respawnRender() {
        new Thread(new RespawnTask(this)).start();
    }


    public boolean isDead() { return isDead; }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public int getScore() {
        return score;
    }

    public void increaseScore() {
        score += 1;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
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
