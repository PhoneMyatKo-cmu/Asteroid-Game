package se233.project2.controller;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.util.Duration;
import se233.project2.model.*;
import se233.project2.view.GameStage;

import java.util.ArrayList;
import java.util.Iterator;

public class DrawingLoop implements Runnable {
    private GameStage gameStage;
    private PlayerShip playerShip;
    private ArrayList<MovingObject> enemyList;
    private long interval = 1000/60;
    private int running = 10;

    public DrawingLoop(GameStage gameStage) {
        this.gameStage = gameStage;
        this.playerShip = gameStage.getPlayerShip();
        this.enemyList = gameStage.getEnemyList();
    }

    private void bossRoundTransistion() {
        Label warningLabel = new Label("Final Boss Incoming!!!");
        Platform.runLater(() -> {
            warningLabel.setStyle("-fx-font-size: 40px; -fx-text-fill: red; -fx-alignment: center");
            warningLabel.setPrefWidth(GameStage.WIDTH);
            warningLabel.setLayoutX(0);
            warningLabel.setLayoutY(GameStage.HEIGHT/2);
            gameStage.getChildren().add(warningLabel);

            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(event -> gameStage.getChildren().remove(warningLabel));
            pause.play();
            Timeline timeline=new Timeline(new KeyFrame(Duration.millis(1000), event -> {gameStage.getBossHealthBar().setVisible(true);}));
            timeline.setCycleCount(1);
            timeline.play();
        });
    }

    private void update() {
        updatePlayerShip();
        updatePlayerBullet();
        updateEnemies();
        updateEnemyBullet();
        if (gameStage.isBossRound() && gameStage.getBoss() != null) {
            bossRoundTransistion();
            Platform.runLater(() -> gameStage.getChildren().add(gameStage.getBoss()));
            gameStage.setBossRound(false);
        }

        Platform.runLater(() -> {
            gameStage.getScoreLabel().setText(playerShip.getScore() + "");
        });
    }

    private void updatePlayerShip() {
        playerShip.draw();
        if (playerShip.isDead()) {
            if (playerShip.isActive()) {
                Platform.runLater(() -> gameStage.getHpBox().getChildren().removeLast());
                playerShip.deathRender();
                playerShip.setActive(false);
            } else if (playerShip.getHp() > 0) {
                playerShip.respawnRender();
            }
        }
    }

    private void updatePlayerBullet() {
        ArrayList<Bullet> playerBulletListCloned = (ArrayList<Bullet>) playerShip.getBulletList().clone();
        ArrayList<Bullet> playerBulletToBeRemoved = new ArrayList<>();
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
    }

    private void updateEnemies() {
        ArrayList<MovingObject> enemyListCloned = (ArrayList<MovingObject>) enemyList.clone();
        ArrayList<MovingObject> enemyToBeRemoved = new ArrayList<>();
        synchronized (enemyListCloned) {
            Iterator<MovingObject> iterator = enemyListCloned.iterator();
            while (iterator.hasNext()) {
                MovingObject movingObject = iterator.next();
                if (movingObject.isDead()) {
                    enemyToBeRemoved.add(movingObject);
                    Platform.runLater(() -> {
                        gameStage.getChildren().remove(movingObject);
                    });
                    if (movingObject instanceof Asteroid) {
                        Asteroid.explode(gameStage, (Asteroid) movingObject);
                    }
                }
                if (movingObject instanceof EnemyShip)
                    ((EnemyShip) movingObject).aimPlayerShip(playerShip);
                if (movingObject instanceof Boss)
                    ((Boss) movingObject).aimPlayerShip(playerShip);
                movingObject.draw();
            }
            synchronized (gameStage.getEnemyList()) {
                gameStage.getEnemyList().removeAll(enemyToBeRemoved);
            }
        }
    }

    private void updateEnemyBullet() {
        ArrayList<Bullet> enemyBulletCloned = (ArrayList<Bullet>) GameStage.enemyBulletList.clone();
        ArrayList<Bullet> bulletToBeRemoved = new ArrayList<>();
        for (Bullet bullet : enemyBulletCloned) {
            if (bullet.isDead()) {
                bulletToBeRemoved.add(bullet);
                Platform.runLater(() -> gameStage.getChildren().remove(bullet));
            }
            bullet.draw();
        }
        synchronized (GameStage.enemyBulletList) {
            GameStage.enemyBulletList.removeAll(bulletToBeRemoved);
        }
    }

    @Override
    public void run() {
        while (running > 0) {
            long startTime = System.currentTimeMillis();
            update();
            if (!gameStage.isRunning())
                running--;
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
        Platform.runLater(() -> {
            if(gameStage.isWon())
                GameStageController.changeToGameOver("Victory",gameStage);
            else
                 GameStageController.changeToGameOver("Game Over",gameStage);
        });
    }

}
