package se233.project2.view;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import se233.project2.Launcher;
import se233.project2.controller.GameStageController;
import se233.project2.model.*;

import java.util.ArrayList;

public class GameStage extends Pane {
    public static final double WIDTH = 800;
    public static final double HEIGHT = 600;
    private ArrayList<MovingObject> enemyList;
    private PlayerShip playerShip;
    private Keys keys;
    private Image bgImage;
    private Label scoreLabel;

    public GameStage() {
        this.getStylesheets().add(Launcher.class.getResource("styles.css").toExternalForm());
        playerShip = new PlayerShip(350, 500,
                new AnimatedSprite(new Image(Launcher.class.getResourceAsStream("playerShip3_red.png")), 1, 1, 1, 0, 0, 98, 75),
                3, 98, 75);
        enemyList = new ArrayList<>();
        keys = new Keys();
        bgImage = new Image(Launcher.class.getResourceAsStream("bg.jpg"));
        ImageView bgView = new ImageView(bgImage);
        bgView.setFitWidth(WIDTH);
        bgView.setFitHeight(HEIGHT);
        scoreLabel = new Label("0");
        scoreLabel.getStyleClass().add("scoreLabel");
        Label countdownLabel = new Label();
        countdownLabel.getStyleClass().add("countdownLabel");
        countdownLabel.layoutXProperty().bind(this.widthProperty().subtract(countdownLabel.widthProperty()).divide(2));
        countdownLabel.layoutYProperty().bind(this.heightProperty().subtract(countdownLabel.heightProperty()).divide(2));
        getChildren().addAll(bgView, scoreLabel, playerShip,countdownLabel);
        GameStageController.onLoad(countdownLabel, this);
    }

    public PlayerShip getPlayerShip() {
        return playerShip;
    }

    public void setPlayerShip(PlayerShip playerShip) {
        this.playerShip = playerShip;
    }

    public ArrayList<MovingObject> getEnemyList() {
        return enemyList;
    }

    public void setEnemyList(ArrayList<MovingObject> enemyList) {
        this.enemyList = enemyList;
    }

    public Keys getKeys() {
        return keys;
    }
}
