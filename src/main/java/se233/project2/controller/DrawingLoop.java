package se233.project2.controller;

import javafx.application.Platform;
import se233.project2.model.*;
import se233.project2.view.GameStage;

import java.util.ArrayList;
import java.util.Iterator;

public class DrawingLoop implements Runnable {
    private GameStage gameStage;
    private PlayerShip playerShip;
    private ArrayList<MovingObject> enemyList;
    private long interval = 1000/60;

    public DrawingLoop(GameStage gameStage) {
        this.gameStage = gameStage;
        this.playerShip = gameStage.getPlayerShip();
        this.enemyList = gameStage.getEnemyList();
    }

    private void update() {
        playerShip.draw();
        if (playerShip.isDead() && playerShip.isActive()) {
            DeathRenderTask task = new DeathRenderTask(playerShip);
            (new Thread(task)).start();
        }
        ArrayList<Bullet> playerBulletListCloned = (ArrayList<Bullet>) playerShip.getBulletList().clone();
        ArrayList<Bullet> playerBulletToBeRemoved = new ArrayList<>();
        ArrayList<MovingObject> enemyListCloned = (ArrayList<MovingObject>) enemyList.clone();
        ArrayList<MovingObject> enemyToBeRemoved = new ArrayList<>();
        synchronized (playerBulletListCloned) {
            Iterator<Bullet> iterator = playerBulletListCloned.iterator();
            while (iterator.hasNext()) {
                Bullet bullet = iterator.next();
                if (bullet.isDead()) {
                    playerBulletToBeRemoved.add(bullet);
                    Platform.runLater(() -> gameStage.getChildren().remove(bullet));
                }
                bullet.draw();
            }
        }
        synchronized (playerShip.getBulletList()) {
            playerShip.getBulletList().removeAll(playerBulletToBeRemoved);
        }

        synchronized (enemyListCloned) {
            Iterator<MovingObject> iterator = enemyListCloned.iterator();
            while (iterator.hasNext()) {
                MovingObject movingObject = iterator.next();
                if (movingObject.isDead()) {
                    enemyToBeRemoved.add(movingObject);
                    Platform.runLater(() -> {
                        gameStage.getChildren().remove(movingObject);
                        new Explosion(gameStage, movingObject.getX(), movingObject.getY());
                    });
                    if (movingObject instanceof Asteroid) {
                        Asteroid.explode(gameStage, (Asteroid) movingObject);
                    }
                }
                movingObject.draw();
            }
            synchronized (gameStage.getEnemyList()) {
                gameStage.getEnemyList().removeAll(enemyToBeRemoved);
            }
        }

    }

    @Override
    public void run() {
        while (true) {
            long startTime = System.currentTimeMillis();
            update();
            long elapsedTime = System.currentTimeMillis() - startTime;
            try {
                if (elapsedTime <= interval) {
                    Thread.sleep(interval - elapsedTime);
                } else {
                    Thread.sleep(interval - elapsedTime % interval);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
