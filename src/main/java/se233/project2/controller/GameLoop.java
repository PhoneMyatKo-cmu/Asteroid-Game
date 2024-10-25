package se233.project2.controller;

import javafx.application.Platform;
import se233.project2.model.*;
import se233.project2.view.GameStage;

import java.util.ArrayList;

public class GameLoop implements Runnable {
    private GameStage gameStage;
    private PlayerShip playerShip;
    private ArrayList<MovingObject> enemyList;
    private long interval = 1000/20;

    public GameLoop(GameStage gameStage) {
        this.gameStage = gameStage;
        this.playerShip = gameStage.getPlayerShip();
        this.enemyList = (ArrayList<MovingObject>) gameStage.getEnemyList();
    }

    private void updatePlayerShip() {
        boolean upPressed = gameStage.getKeys().isPressed(playerShip.getMoveUpKey());
        boolean downPressed = gameStage.getKeys().isPressed(playerShip.getMoveDownKey());
        boolean leftPressed = gameStage.getKeys().isPressed(playerShip.getMoveLeftKey());
        boolean rightPressed = gameStage.getKeys().isPressed(playerShip.getMoveRightKey());
        boolean turnLeftPressed = gameStage.getKeys().isPressed(playerShip.getTurnLeftKey());
        boolean turnRightPressed = gameStage.getKeys().isPressed(playerShip.getTurnRightKey());
        boolean shootPressed = gameStage.getKeys().isPressed(playerShip.getShootKey());
        if (upPressed && downPressed) {
            playerShip.stop();
        } else if (leftPressed && rightPressed) {
            playerShip.stop();
        } else if (upPressed) {
            playerShip.moveUp();
        } else if (downPressed) {
            playerShip.moveDown();
        } else if (leftPressed) {
            playerShip.moveLeft();
        } else if (rightPressed) {
            playerShip.moveRight();
        } else {
            playerShip.stop();
        }
        if (turnLeftPressed && turnRightPressed) {

        } else if (turnLeftPressed) {
            playerShip.turnLeft();
        } else if (turnRightPressed) {
            playerShip.turnRight();
        }
        if (shootPressed) {
            playerShip.shoot();
        }
    }

    private void update() {
        playerShip.move();
        ArrayList<MovingObject> enemyListCloned = (ArrayList<MovingObject>) enemyList.clone();
        ArrayList<Bullet> bulletListCloned = (ArrayList<Bullet>) playerShip.getBulletList().clone();
        ArrayList<Bullet> enemyBulletListCloned = (ArrayList<Bullet>) GameStage.enemyBulletList.clone();
        for (MovingObject movingObject: enemyListCloned) {
            movingObject.move();
            if (playerShip.isCollided(movingObject) && !playerShip.isDead()) {
                playerShip.stop();
                playerShip.die();
            }
            if (movingObject instanceof EnemyShip) {
                ((EnemyShip) movingObject).shoot();
            }
            if (movingObject instanceof Boss) {
                ((Boss) movingObject).shoot();
            }
        }
        for (Bullet bullet: bulletListCloned) {
            bullet.move();
            for (MovingObject movingObject: enemyListCloned) {
                if (bullet.isCollided(movingObject)) {
                    bullet.die();
                    movingObject.die();
                    Platform.runLater(() -> new Explosion(gameStage, movingObject.getX(), movingObject.getY()));
                    if (movingObject instanceof Asteroid && ((Asteroid) movingObject).getLevel() == 1) {
                        playerShip.increaseScore();
                    }
                    break;
                }
            }
        }
        for (Bullet bullet: enemyBulletListCloned) {
            bullet.move();
            if (bullet.isCollided(playerShip) && !playerShip.isDead()) {
                bullet.die();
                playerShip.stop();
                playerShip.die();
            }
        }

        if (gameStage.isBossRound() && enemyList.isEmpty()) {
            Boss boss = Boss.generateBoss();
            gameStage.setBoss(boss);
            enemyList.add(boss);
        }
    }

    @Override
    public void run() {
        while (gameStage.isRunning()){
            long startTime = System.currentTimeMillis();
            updatePlayerShip();
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
