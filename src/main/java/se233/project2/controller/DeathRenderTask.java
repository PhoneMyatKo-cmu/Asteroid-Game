package se233.project2.controller;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import se233.project2.model.Explosion;
import se233.project2.model.PlayerShip;

public class DeathRenderTask implements Runnable {
    private PlayerShip playerShip;

    public DeathRenderTask(PlayerShip playerShip) {
        this.playerShip = playerShip;
    }

    @Override
    public void run() {
        Platform.runLater(() -> {
            playerShip.setVisible(false);
            Explosion explosion = new Explosion((Pane) playerShip.getParent(), playerShip.getX(), playerShip.getY());
            playerShip.setActive(false);
        });

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        playerShip.setX(350);
        playerShip.setY(500);
        Platform.runLater(() -> {
            playerShip.setVisible(true);
            playerShip.setDead(false);
            playerShip.setActive(true);
        });
    }

}
