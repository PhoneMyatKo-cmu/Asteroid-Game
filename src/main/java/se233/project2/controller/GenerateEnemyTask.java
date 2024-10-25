package se233.project2.controller;

import javafx.application.Platform;
import javafx.scene.image.Image;
import se233.project2.Launcher;
import se233.project2.model.*;
import se233.project2.view.GameStage;

import java.util.ArrayList;
import java.util.Random;

public class GenerateEnemyTask implements Runnable {
    private ArrayList<MovingObject> enemyList;
    private GameStage gameStage;
    private Random rand;
    private int asteroidLimit = 20;

    public GenerateEnemyTask(GameStage gameStage) {
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
        vx = rand.nextDouble(-1.5, 1.5);
        vy = rand.nextDouble(1.5);
        int level = rand.nextInt(1,4);
        Asteroid asteroid = new Asteroid(x, y, 0, 0,
                new AnimatedSprite(new Image(Launcher.class.getResourceAsStream("sprite_sheet/animated_asteroid2.png")), 16, 16, 1, 0, 0, 60, 60),
                level, level);
        asteroid.setVx(vx);
        asteroid.setVy(vy);
        enemyList.add(asteroid);
        Platform.runLater(() -> gameStage.getChildren().add(asteroid));
        asteroidLimit -= level;
    }

    private void generateEnemyShip() {
        Random rand = new Random();
        double y = rand.nextDouble(100);
        AnimatedSprite animatedSprite = new AnimatedSprite(new Image(Launcher.class.getResourceAsStream("enemyBlack3.png")), 1, 1, 1, 0, 0, 103, 84);
        EnemyShip enemyShip = new EnemyShip(0, y, 0, 0, animatedSprite, 1, 60, 50);
        enemyShip.setVx(5);
        enemyList.add(enemyShip);
        Platform.runLater(() -> gameStage.getChildren().add(enemyShip));
    }



    @Override
    public void run() {
        for (int count = 0; asteroidLimit > 0 && gameStage.isRunning(); count++) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            int numberOfAsteroids = rand.nextInt(1, 4);
            for (int i = 0; i < numberOfAsteroids; i++) {
                generateAsteroid();
            }

            if (count % 3 == 0)
                generateEnemyShip();
        }
        if (asteroidLimit <= 0) {
            gameStage.setBossRound(true);
        }
    }
}
