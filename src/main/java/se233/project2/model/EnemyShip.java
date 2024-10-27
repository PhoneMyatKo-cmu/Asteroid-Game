package se233.project2.model;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import se233.project2.Launcher;
import se233.project2.view.GameStage;

import java.util.Random;

public class EnemyShip extends Character {
    private long lastShotTime;
    private int fireRate = 1;

    public EnemyShip(double x, double y, double ax, double ay, AnimatedSprite animatedSprite, int hp, double width, double height) {
        super(x, y, ax, ay, animatedSprite, hp, width, height);
        this.animatedSprite.setRotate(180);
    }

    public void die() {
        isDead = true;
    }

    public void aimPlayerShip(PlayerShip playerShip) {
        if (this.getY() > playerShip.getY()) {
            Platform.runLater(() -> this.animatedSprite.setRotate(180));
        } else {
            Platform.runLater(() -> this.animatedSprite.setRotate(0));
        }
    }

    public void shoot() {
        if (System.currentTimeMillis() - lastShotTime < 1000/fireRate) {
            return;
        }
        double bx = Math.sin(Math.toRadians(animatedSprite.getRotate())) * animatedSprite.getFitHeight()/2 + getX();
        double by = -Math.cos(Math.toRadians(animatedSprite.getRotate())) * animatedSprite.getFitHeight()/2 + getY();

        Bullet bullet = new Bullet(bx, by, 0, 0, new AnimatedSprite(new Image(Launcher.class.getResource("bullet_sprite_red.png").toString()), 4, 4, 1, 0, 234, 105, 117), 30, 25);

        bullet.setVy(15);
        bullet.setBulletLife(35);
        Platform.runLater(() -> bullet.animatedSprite.setRotate(animatedSprite.getRotate() - 90));
        GameStage.enemyBulletList.add(bullet);
        Platform.runLater(() -> ((Pane) getParent()).getChildren().add(bullet));
        lastShotTime = System.currentTimeMillis();
    }
}
