package se233.project2;

import javafx.scene.image.Image;
import org.apache.logging.log4j.core.util.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se233.project2.model.AnimatedSprite;
import se233.project2.model.Asteroid;
import se233.project2.model.MovingObject;
import se233.project2.model.PlayerShip;
import se233.project2.view.GameStage;

import static org.junit.jupiter.api.Assertions.*;

public class MovingObjectTest {
    MovingObject movingObject;

    @BeforeEach
    public void setup() {
        movingObject = new PlayerShip(0, 0, new AnimatedSprite(new Image(getClass().getResourceAsStream("playership_sprite.png")), 3, 3, 1, 0, 0, 124, 240), 3, 62, 120);
    }

    @Test
    public void move_GivenPositiveAccelerationX_ThenVelocityIncreaseAndMoveRight() {
        movingObject.setAx(10);
        movingObject.move();
        assertEquals(10, movingObject.getVx(), "Velocity should be increased by 10");
        assertEquals(10, movingObject.getX(), "Moving Object should move 10 to the Right");
    }

    @Test
    public void move_GivenMoveBeyondBoundary_ThenAppearOnOppositeSide() {
        movingObject.setAx(-10 - movingObject.getWidth()/2);
        movingObject.move();
        assertEquals(GameStage.WIDTH, movingObject.getX(), "MovingObject should appear on the opposite side");
    }

    @Test
    public void isCollided_GivenTwoObjectsOverlap_ThenReturnTrue() {
        MovingObject asteroid = new Asteroid(0, 0, 0, 0, new AnimatedSprite(new Image(getClass().getResourceAsStream("sprite_sheet/animated_asteroid2.png")), 16, 16, 1, 0, 0, 60, 60), 1, 1);
        assertTrue(asteroid.isCollided(movingObject), "Collided, Should return true");
    }

    @Test
    public void isCollided_GivenTwoObjectsDoNotOverlap_ThenReturnFalse() {
        MovingObject asteroid = new Asteroid(300, 300, 0, 0, new AnimatedSprite(new Image(getClass().getResourceAsStream("sprite_sheet/animated_asteroid2.png")), 16, 16, 1, 0, 0, 60, 60), 1, 1);
        assertFalse(asteroid.isCollided(movingObject), "Not collided, Should return false");
    }
}
