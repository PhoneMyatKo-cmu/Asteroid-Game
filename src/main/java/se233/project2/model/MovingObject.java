package se233.project2.model;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import se233.project2.view.GameStage;

public abstract class MovingObject extends Pane {
    private double x, y, vx, vy, ax, ay, drag = 1; // x & y coordinates and velocities
    protected AnimatedSprite animatedSprite;

    public MovingObject(double x, double y, double ax, double ay, AnimatedSprite animatedSprite, double width, double height) {
        this.x = x;
        this.y = y;
        this.ax = ax;
        this.ay = ay;
        this.vx = 0;
        this.vy = 0;
        this.animatedSprite = animatedSprite;
        this.animatedSprite.setFitWidth(width);
        this.animatedSprite.setFitHeight(height);
//        setTranslateX(this.x);
//        setTranslateY(this.y);
        getChildren().add(animatedSprite);
        setWidth(width);
        setHeight(height);
    }

    public void move() {
        vx += ax;
        vy += ay;
        x += vx;
        y += vy;
        if (x > GameStage.WIDTH) {
            x = 0;
        } else if (x < 0) {
            x = GameStage.WIDTH;
        }
        if (y > GameStage.HEIGHT) {
            y = 0;
        } else if (y < 0) {
            y = GameStage.HEIGHT;
        }
        Platform.runLater( () -> {
            animatedSprite.start();
            animatedSprite.setTranslateX(x - animatedSprite.getFitWidth()/2);
            animatedSprite.setTranslateY(y - animatedSprite.getFitHeight()/2);
        });
    }

    public void stop() {
        ax = 0; ay = 0;
        if (vx > 0) {
            vx = (vx - drag) >= 0 ? (vx - drag) : 0;
        } else {
            vx = (vx + drag) <= 0 ? (vx + drag) : 0;
        }
        if (vy > 0) {
            vy = (vy - drag) >= 0 ? (vy - drag) : 0;
        } else {
            vy = (vy + drag) <= 0 ? (vy + drag) : 0;
        }
    }

    public boolean isCollided(MovingObject movingObject) {
        return this.getBoundsInParent().intersects(movingObject.getBoundsInParent());
    }

    public double getAx() {
        return ax;
    }

    public void setAx(double ax) {
        this.ax = ax;
    }

    public double getAy() {
        return ay;
    }

    public void setAy(double ay) {
        this.ay = ay;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getVx() {
        return vx;
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public double getVy() {
        return vy;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }

    public AnimatedSprite getAnimatedSprite() {
        return animatedSprite;
    }

    public void setAnimatedSprite(AnimatedSprite animatedSprite) {
        this.animatedSprite = animatedSprite;
    }
}
