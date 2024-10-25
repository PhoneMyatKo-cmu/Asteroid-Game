package se233.project2.controller;

import javafx.application.Platform;
import se233.project2.model.PlayerShip;

public class RespawnTask implements Runnable {
    private PlayerShip playerShip;

    public RespawnTask(PlayerShip playerShip) {
        this.playerShip = playerShip;
    }

    @Override
    public void run() {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        playerShip.setX(350);
        playerShip.setY(500);
        playerShip.getAnimatedSprite().setRotate(0);
        Platform.runLater(() -> {
            playerShip.setVisible(true);
            playerShip.setDead(false);
            playerShip.setActive(true);
        });
    }

}
