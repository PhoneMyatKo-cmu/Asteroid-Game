package se233.project2;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se233.project2.model.AnimatedSprite;
import se233.project2.model.Bullet;
import se233.project2.model.PlayerShip;

public class PlayerShipTest {
    PlayerShip playerShip;

    @BeforeEach
    public void setup() {
        Pane pane = new Pane();
        playerShip = new PlayerShip(0, 0, new AnimatedSprite(new Image(getClass().getResourceAsStream("playership_sprite.png")), 3, 3, 1, 0, 0, 124, 240), 3, 62, 120, 3);
        pane.getChildren().add(playerShip);
    }

    @Test
    public void turnLeftTest() {
        playerShip.turnLeft();
        Platform.runLater( () -> {
        Assertions.assertEquals(-5, playerShip.getAnimatedSprite().getRotate(), "Rotate 5 degrees to the left");
        });
    }

    @Test
    public void turnRightTest() {
        playerShip.turnRight();
        Platform.runLater( () -> {
        Assertions.assertEquals(5, playerShip.getAnimatedSprite().getRotate(), "Rotate 5 degrees to the right");
        });
    }

    @Test
    public void shoot_givenShootOnce_thenBulletListHasOneBullet() {
        playerShip.shoot();
        Assertions.assertEquals(1, playerShip.getBulletList().size(), "BulletList has 1 bullet");
    }

}
