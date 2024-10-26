package se233.project2.model;

import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;
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
    private KeyCode shootLaserKey = KeyCode.UP;
    private boolean isDead, isActive;
    private final int bulletSpeed = 20;
    private ArrayList<Bullet> bulletList;
    private long lastShotTime = 0;
    private int fireRate = 3;
    private int score;
    private Polygon hitbox;
    private final Double[] points = {
            31.25, 11.5,
            6.25, 44.0,
            31.25, 52.5,
            55.25, 44.0
    };

    public PlayerShip(double x, double y, AnimatedSprite animatedSprite, int hp, double width, double height) {
        super(x, y, 0, 0, animatedSprite, hp, width, height);
        isDead = false;
        isActive = true;
        bulletList = new ArrayList<>();
        score = 0;
        hitbox = new Polygon();
        hitbox.getPoints().addAll(points);
        hitbox.setFill(Color.TRANSPARENT);
        this.getChildren().add(hitbox);
    }

    @Override
    public boolean isCollided(MovingObject movingObject) {
        if (isDead) return false;
        synchronized (hitbox) {
            Bounds boundsInScene = hitbox.localToScene(hitbox.getBoundsInLocal());
            Bounds boundsInOuter = ((GameStage) this.getParent()).sceneToLocal(boundsInScene);
            return boundsInOuter.intersects(movingObject.getBoundsInParent());
        }
    }

    public void moveRight() { setAx(1); }
    public void moveLeft() {
        setAx(-1);
    }
    public void moveUp() { setAy(-1); }
    public void moveDown() { setAy(1); }

    public void turnLeft() {
        Platform.runLater(() -> {
            animatedSprite.setRotate(animatedSprite.getRotate() - 5);
            Rotate rotate = new Rotate(-5, getWidth()/2, getHeight()/2);
            hitbox.getTransforms().add(rotate);
        });
    }
    public void turnRight() {
        Platform.runLater(() -> {
            animatedSprite.setRotate(animatedSprite.getRotate() + 5);
            Rotate rotate = new Rotate(5, getWidth()/2, getHeight()/2);
            hitbox.getTransforms().add(rotate);
        });
    }
    public void die() {
        isDead = true;
        this.setHp(getHp() - 1);
        if (this.getHp() == 0)
            ((GameStage) this.getParent()).setRunning(false);
    }

    public void shoot() {
        if (System.currentTimeMillis() - lastShotTime < 1000/fireRate || !isActive()) {
            return;
        }
        double bvx = Math.sin(Math.toRadians(animatedSprite.getRotate())) * bulletSpeed;
        double bvy = -Math.cos(Math.toRadians(animatedSprite.getRotate())) * bulletSpeed;
        double bx = Math.sin(Math.toRadians(animatedSprite.getRotate())) * animatedSprite.getFitHeight()/2 + getX();
        double by = -Math.cos(Math.toRadians(animatedSprite.getRotate())) * animatedSprite.getFitHeight()/2 + getY();

        Bullet bullet = new Bullet(bx, by, 0, 0, new AnimatedSprite(new Image(Launcher.class.getResource("bullet_sprite_yellow.png").toString()), 4, 4, 1, 0, 0, 100, 69), 30, 25);
        bullet.setVx(bvx);
        bullet.setVy(bvy);
        Platform.runLater(() -> bullet.animatedSprite.setRotate(animatedSprite.getRotate() - 90));
        bulletList.add(bullet);
        Platform.runLater(() -> ((Pane) getParent()).getChildren().add(bullet));
        lastShotTime = System.currentTimeMillis();
    }

    public void shootLaser() {
        if (System.currentTimeMillis() - lastShotTime < 1000 || !isActive()) {
            return;
        }
        double bvx = Math.sin(Math.toRadians(animatedSprite.getRotate())) * bulletSpeed;
        double bvy = -Math.cos(Math.toRadians(animatedSprite.getRotate())) * bulletSpeed;

        double bx = Math.sin(Math.toRadians(animatedSprite.getRotate())) * (animatedSprite.getFitHeight()/2) + getX();
        double by = -Math.cos(Math.toRadians(animatedSprite.getRotate())) * (animatedSprite.getFitHeight()/2) + getY();

        Bomb bomb = new Bomb(bx, by, 0, 0, new AnimatedSprite(new Image(Launcher.class.getResource("laserRed14.png").toString()), 1, 1, 1, 0, 0, 13, 57), 20, 40);
        bomb.setBulletLife(25);
        bomb.setVx(bvx);
        bomb.setVy(bvy);
        Platform.runLater(() -> {
            bomb.animatedSprite.setRotate(animatedSprite.getRotate());
        });
        Platform.runLater(() -> ((Pane) getParent()).getChildren().add(bomb));
        bulletList.add(bomb);
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

    public KeyCode getShootLaserKey() {
        return shootLaserKey;
    }

    public Polygon getHitbox() {
        return hitbox;
    }
}
