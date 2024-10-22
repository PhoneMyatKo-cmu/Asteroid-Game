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
        });

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Platform.runLater(() -> {
            playerShip.setVisible(true);
            playerShip.setDead(false);
        });
    }

}
