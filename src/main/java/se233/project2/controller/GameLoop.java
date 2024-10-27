package se233.project2.controller;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import se233.project2.model.*;
import se233.project2.view.GameMenu;
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
        boolean laserPressed = gameStage.getKeys().isPressed(playerShip.getShootLaserKey());
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
        if (laserPressed) {
            playerShip.shootBomb();
        } else if (shootPressed) {
            playerShip.shoot();
        }
    }

    private void update() {
        if (playerShip.isActive())
            playerShip.move();
        ArrayList<MovingObject> enemyListCloned = (ArrayList<MovingObject>) enemyList.clone();
        ArrayList<Bullet> bulletListCloned = (ArrayList<Bullet>) playerShip.getBulletList().clone();
        ArrayList<Bullet> enemyBulletListCloned = (ArrayList<Bullet>) GameStage.enemyBulletList.clone();
        for (MovingObject movingObject: enemyListCloned) {
            movingObject.move();
            if (playerShip.isActive() && !playerShip.isImmune() && playerShip.isCollided(movingObject)) {
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
                if (bullet.getParent() == null) {
                    break;
                }
                if (bullet.isCollided(movingObject)) {
                    movingObject.die();
                    if(movingObject instanceof  Boss){
                        Platform.runLater(()->gameStage.updateBossHealth(((Boss) movingObject).getHp()));
                    }
                    Platform.runLater(() -> new Explosion(gameStage, movingObject.getX(), movingObject.getY()));
                    if (movingObject instanceof Asteroid && ((Asteroid) movingObject).getLevel() == 1) {
                        playerShip.increaseScore(1);
                    } else if (movingObject instanceof EnemyShip) {
                        playerShip.increaseScore(3);
                    } else if (movingObject instanceof Boss) {
                        playerShip.increaseScore(10);
                    }
                    if (bullet instanceof Bomb) {
                        Platform.runLater(() -> new Explosion(gameStage, bullet.getX(), bullet.getY(), 300.0, 300.0));
                        Circle circle = new Circle(bullet.getX(), bullet.getY(), 100, Color.TRANSPARENT);
                        Platform.runLater(() -> gameStage.getChildren().add(circle));
                        for (MovingObject mv : enemyListCloned) {
                            if (circle.intersects(mv.getBoundsInParent())) {
                                mv.die();
                                if(mv instanceof Boss) {
                                    ((Boss) mv).setHp(((Boss) mv).getHp() - 3);
                                    Platform.runLater(() -> gameStage.updateBossHealth(((Boss) mv).getHp()));
                                }

                                Platform.runLater(() -> new Explosion(gameStage, mv.getX(), mv.getY()));
                                if (mv instanceof Asteroid) {
                                    if (((Asteroid) mv).getLevel() == 1)
                                        playerShip.increaseScore(1);
                                } else if (movingObject instanceof EnemyShip) {
                                    playerShip.increaseScore(3);
                                } else if (movingObject instanceof Boss) {
                                    playerShip.increaseScore(10);
                                }

                            }
                        }
                        Platform.runLater(() -> gameStage.getChildren().remove(circle));
                    }
                    if(movingObject instanceof Boss && movingObject.isDead()){
                        gameStage.setRunning(false);
                        gameStage.setWon(true);
                    }
                    bullet.die();
                    break;
                }


            }
        }
        for (Bullet bullet: enemyBulletListCloned) {
            bullet.move();
            if (playerShip.isActive() && !playerShip.isImmune() && playerShip.isCollided(bullet)) {
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
