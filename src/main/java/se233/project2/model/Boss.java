package se233.project2.model;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import se233.project2.Launcher;
import se233.project2.view.GameStage;

import java.util.HashMap;

public class Boss  extends Character{
    private long lastShotTime = 0;
    private int fireRate = 1;
    private double bulletSpeed = 15;

    public Boss(double x, double y, double vx, double vy, AnimatedSprite animatedSprite, int hp, double width, double height) {
        super(x, y, vx, vy, animatedSprite, hp, width, height);
    }

    @Override
    public void die() {
        setHp(getHp() - 1);
        if (getHp() <= 0) {
            isDead = true;
        }
    }

    @Override
    public void move() {
        setVx(getVx() + getAx());
        setVy(getVy() + getAy());
        setX(getX() + getVx());
        setY(getY() + getVy());

        double x = getX(), y = getY();
        if (x + animatedSprite.width/2 >= GameStage.WIDTH) {
            setVx(-getVx());
        } else if (x - animatedSprite.width/2 <= 0) {
            setVx(-getVx());
        }
        if (y >= GameStage.HEIGHT + animatedSprite.height/2) {
            setY(0);
        }
    }

    public void shoot() {
        if (System.currentTimeMillis() - lastShotTime < 1000/fireRate) {
            return;
        }
        double bvx = Math.sin(Math.toRadians(animatedSprite.getRotate() + 180)) * bulletSpeed;
        double bvy = -Math.cos(Math.toRadians(animatedSprite.getRotate() + 180)) * bulletSpeed;
        double bx = -Math.sin(Math.toRadians(animatedSprite.getRotate())) * animatedSprite.getFitHeight()/2 + getX();
        double by = Math.cos(Math.toRadians(animatedSprite.getRotate())) * animatedSprite.getFitHeight()/2 + getY();

        Media shotSound = new Media(Launcher.class.getResource("audio/enemy_bulletSound.mp3").toString());
        MediaPlayer shotPlayer = new MediaPlayer(shotSound);
        shotPlayer.play();
        Bullet bullet = new Bullet(bx, by, 0, 0, new AnimatedSprite(new Image(Launcher.class.getResource("bullet_sprite_red.png").toString()), 4, 4, 1, 0, 234, 105, 117), 30, 25);
        bullet.setVx(bvx);
        bullet.setVy(bvy);
        Platform.runLater(() -> bullet.animatedSprite.setRotate(animatedSprite.getRotate() + 90));
        GameStage.enemyBulletList.add(bullet);
        Platform.runLater(() -> ((Pane) getParent()).getChildren().add(bullet));
        lastShotTime = System.currentTimeMillis();
    }

    public static Boss generateBoss() {
        AnimatedSprite animatedSprite = new AnimatedSprite(new Image(Launcher.class.getResourceAsStream("enemyBlack3.png")), 1, 1, 1, 0, 0, 103, 84);
        Boss boss = new Boss(GameStage.WIDTH/2, 0, 0, 0, animatedSprite, 50, 103, 84);
        boss.setVx(3);
        boss.setVy(1.5);
        return boss;
    }

    public void aimPlayerShip(PlayerShip playerShip) {
        double deltaX = playerShip.getX() - this.getX();
        double deltaY = playerShip.getY() - this.getY();
        double angle = Math.atan2(deltaY, deltaX);
        Platform.runLater(() -> this.animatedSprite.setRotate(Math.toDegrees(angle) - 90));
    }
}
