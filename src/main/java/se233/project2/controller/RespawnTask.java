package se233.project2.controller;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import se233.project2.model.PlayerShip;

public class RespawnTask implements Runnable {
    private PlayerShip playerShip;

    public RespawnTask(PlayerShip playerShip) {
        this.playerShip = playerShip;
    }

    @Override
    public void run() {

        playerShip.setDead(false);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        playerShip.setX(350);
        playerShip.setY(500);
        Rotate rotate = new Rotate(-playerShip.getAnimatedSprite().getRotate(), playerShip.getWidth()/2, playerShip.getHeight()/2);

        Platform.runLater(() -> {
            playerShip.getHitbox().getTransforms().add(rotate);
            playerShip.getAnimatedSprite().setRotate(0);
            playerShip.setVisible(true);
            playerShip.setActive(true);
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(200), playerShip);
            fadeTransition.setFromValue(1.0);
            fadeTransition.setToValue(0.0);
            fadeTransition.setCycleCount(14);
            fadeTransition.setAutoReverse(true);
            fadeTransition.setOnFinished(event -> playerShip.setImmune(false));
            fadeTransition.play();
        });
    }

}
