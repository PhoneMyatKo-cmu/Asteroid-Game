package se233.project2.model;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import se233.project2.Launcher;


public class Explosion extends ImageView {
    private Pane gamePane;

    public Explosion(Pane gamePane, double x, double y) {
        this(gamePane, x, y, 100, 100);
    }

    public Explosion(Pane gamePane, double x, double y, double width, double height) {
        this.gamePane = gamePane;
        setImage(new Image(Launcher.class.getResourceAsStream("sprite_sheet/explosion_01_strip13.png")));  // Path to your explosion image
        setFitWidth(width);  // Adjust the size of the explosion
        setFitHeight(height);
        setLayoutX(x - width/2);
        setLayoutY(y - height/2);
        this.setViewport(new Rectangle2D(424.67, 0, 212.33, 190));
        this.gamePane.getChildren().add(this);  // Add explosion to the game scene

        Media explosionSound = new Media(Launcher.class.getResource("audio/Explosion.wav").toString());
        MediaPlayer explosionPlayer = new MediaPlayer(explosionSound);
        explosionPlayer.play();
        // Optional fade-out effect to make the explosion disappear smoothly
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), this);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setOnFinished(event -> gamePane.getChildren().remove(this));  // Remove after fading
        fadeTransition.play();
    }
}
