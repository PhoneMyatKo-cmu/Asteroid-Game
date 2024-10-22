package se233.project2.model;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import se233.project2.Launcher;


public class Explosion extends ImageView {
    private Pane gamePane;

    public Explosion(Pane gamePane, double x, double y) {
        this.gamePane = gamePane;
        setImage(new Image(Launcher.class.getResourceAsStream("Explosion.png")));  // Path to your explosion image
        setFitWidth(50);  // Adjust the size of the explosion
        setFitHeight(50);
        setLayoutX(x);
        setLayoutY(y);
        this.gamePane.getChildren().add(this);  // Add explosion to the game scene

        // Optional fade-out effect to make the explosion disappear smoothly
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), this);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setOnFinished(event -> gamePane.getChildren().remove(this));  // Remove after fading
        fadeTransition.play();
    }
}
