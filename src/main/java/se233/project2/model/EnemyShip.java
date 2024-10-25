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

    public void shoot() {
        if (System.currentTimeMillis() - lastShotTime < 1000/fireRate) {
            return;
        }
        double bx = Math.sin(Math.toRadians(animatedSprite.getRotate())) * animatedSprite.getFitHeight()/2 + getX();
        double by = -Math.cos(Math.toRadians(animatedSprite.getRotate())) * animatedSprite.getFitHeight()/2 + getY();

        Bullet bullet = new Bullet(bx, by, 0, 0, new AnimatedSprite(new Image(Launcher.class.getResource("laserGreen09.png").toString()), 1, 1, 1, 0, 0, 13, 57), 13, 20);

        bullet.setVy(15);
        Platform.runLater(() -> bullet.animatedSprite.setRotate(animatedSprite.getRotate()));
        GameStage.enemyBulletList.add(bullet);
        Platform.runLater(() -> ((Pane) getParent()).getChildren().add(bullet));
        lastShotTime = System.currentTimeMillis();
    }
}
