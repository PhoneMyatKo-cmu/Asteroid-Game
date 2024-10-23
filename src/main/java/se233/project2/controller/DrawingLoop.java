package se233.project2.controller;

import javafx.application.Platform;
import se233.project2.model.Bullet;
import se233.project2.model.MovingObject;
import se233.project2.model.PlayerShip;
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
        playerShip.move();
        ArrayList<Bullet> playerBulletList = (ArrayList<Bullet>) playerShip.getBulletList().clone();
        ArrayList<Bullet> playerBulletToBeRemoved = new ArrayList<>();
        for (MovingObject movingObject : enemyList) {
            movingObject.move();
        }
        synchronized (playerBulletList) {
            Iterator<Bullet> iterator = playerBulletList.iterator();
            while (iterator.hasNext()) {
                Bullet bullet = iterator.next();
                if (bullet.isDead()) {
                    playerBulletToBeRemoved.add(bullet);
                    Platform.runLater(() -> gameStage.getChildren().remove(bullet));
                }
                bullet.move();
            }
        }
        playerShip.getBulletList().removeAll(playerBulletToBeRemoved);

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
