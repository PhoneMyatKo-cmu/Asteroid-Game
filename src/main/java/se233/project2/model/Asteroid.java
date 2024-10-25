package se233.project2.model;

import javafx.application.Platform;
import javafx.scene.image.Image;
import se233.project2.Launcher;
import se233.project2.view.GameStage;

import java.util.Random;

public class Asteroid extends Character {
    private int level;
    private boolean isDead;

    public Asteroid(double x, double y, double ax, double ay, AnimatedSprite animatedSprite, int hp, int level) {
        super(x, y, ax, ay, animatedSprite, hp, 60 * level, 60 * level);
        isDead = false;
        this.level = level;
    }

    public static void explode(GameStage gameStage, Asteroid asteroid) {
        int level = asteroid.level - 1;
        if (level == 0) {
            return;
        }

        Random r = new Random();
        for (int i = 0; i < 2; i++) {
            double vx = r.nextDouble(-1.5, 1.5);
            double vy = r.nextDouble(1.5);
            Asteroid newAsteroid = new Asteroid(asteroid.getX(), asteroid.getY(), 0, 0,
                    new AnimatedSprite(new Image(Launcher.class.getResourceAsStream("sprite_sheet/animated_asteroid2.png")), 16, 16, 1, 0, 0, 60, 60),
                    level, level);
            newAsteroid.setVx(vx);
            newAsteroid.setVy(vy);
            gameStage.getEnemyList().add(newAsteroid);
            Platform.runLater(() -> gameStage.getChildren().add(newAsteroid));
        }
    }

    @Override
    public void die() {
        isDead = true;
    }

    public int getLevel() {
        return level;
    }

    public boolean isDead() {
        return isDead;
    }
}
