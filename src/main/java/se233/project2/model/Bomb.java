package se233.project2.model;


import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import se233.project2.view.GameStage;

public class Bomb extends Bullet {

    public Bomb(double x, double y, double ax, double ay, AnimatedSprite animatedSprite, double width, double height) {
        super(x, y, ax, ay, animatedSprite, width, height);

    }

    @Override
    public boolean isCollided(MovingObject movingObject) {
        if (isDead()) return false;
        return this.getBoundsInParent().intersects(movingObject.getBoundsInParent());
    }
}
