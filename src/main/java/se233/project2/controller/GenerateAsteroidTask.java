package se233.project2.controller;

import javafx.application.Platform;
import javafx.scene.image.Image;
import se233.project2.Launcher;
import se233.project2.model.AnimatedSprite;
import se233.project2.model.Asteroid;
import se233.project2.model.MovingObject;
import se233.project2.view.GameStage;

import java.util.ArrayList;
import java.util.Random;

public class GenerateAsteroidTask implements Runnable {
    private ArrayList<MovingObject> enemyList;
    private GameStage gameStage;
    private Random rand;
    private int asteroidLimit = 20;

    public GenerateAsteroidTask(GameStage gameStage) {
        this.gameStage = gameStage;
        this.enemyList = gameStage.getEnemyList();
        rand = new Random();
    }

    private void generateAsteroid() {
        int side = rand.nextInt(1,4);
        double x = 0, y = 0, vx, vy;
        switch (side) {
            case 1:
                x = 0;
                y = rand.nextDouble(0, GameStage.HEIGHT);
                break;
            case 2:
                x = rand.nextDouble(0, GameStage.WIDTH);
                y = 0;
                break;
            case 3:
                x = GameStage.WIDTH;
                y = rand.nextDouble(0, GameStage.HEIGHT);
                break;
        }
        vx = rand.nextDouble(-0.5, 0.5);
        vy = rand.nextDouble(0.5);
        int level = rand.nextInt(1,4);
        Asteroid asteroid = new Asteroid(x, y, 0, 0,
                new AnimatedSprite(new Image(Launcher.class.getResourceAsStream("sprite_sheet/animated_asteroid2.png")), 16, 16, 1, 0, 0, 60, 60),
                level, level);
        asteroid.setVx(vx);
        asteroid.setVy(vy);
        enemyList.add(asteroid);
        Platform.runLater(() -> gameStage.getChildren().add(asteroid));
    }

    @Override
    public void run() {
        while (asteroidLimit > 0) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            int numberOfAsteroids = rand.nextInt(1, 4);
            for (int i = 0; i < numberOfAsteroids; i++) {
                generateAsteroid();
            }
        }
    }
}
