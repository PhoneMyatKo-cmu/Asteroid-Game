package se233.project2.view;

import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
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
    private Boss boss;
    public static ArrayList<Bullet> enemyBulletList;
    private Keys keys;
    private Image bgImage;
    private Label scoreLabel;
    private HBox hpBox;
    private boolean running = true, bossRound = false;
    private  boolean isWon =false;
    private ProgressBar bossHealthBar;
    private Label specialAttackCountLbl;

    public GameStage() {
        this.getStylesheets().add(Launcher.class.getResource("styles.css").toExternalForm());
        playerShip = new PlayerShip(350, 500,
                new AnimatedSprite(new Image(Launcher.class.getResourceAsStream("playership_sprite.png")), 3, 3, 1, 0, 0, 124, 240),
                GameMenu.gameDataMap.getOrDefault("HP", 3), 62, 120);
        enemyList = new ArrayList<>();
        enemyBulletList = new ArrayList<>();
        keys = new Keys();
        bossHealthBar = new ProgressBar();
        bossHealthBar.setProgress(1.0);
        bossHealthBar.setVisible(false);
        bossHealthBar.setTranslateX(600);
        bossHealthBar.setTranslateY(20);
        bossHealthBar.getStyleClass().add("bossHealthBar");
        bossHealthBar.setPrefWidth(150);
        bgImage = new Image(Launcher.class.getResourceAsStream("bg.jpg"));
        hpBox = new HBox(5);
        Image shipHpImage = playerShip.getAnimatedSprite().getImage();
        for (int i = 0; i < playerShip.getHp(); i++) {
            ImageView shipHpImageView = new ImageView(shipHpImage);
            shipHpImageView.setViewport(new Rectangle2D(0, 0, 124, 120));
            shipHpImageView.setFitWidth(25);
            shipHpImageView.setFitHeight(25);
            hpBox.getChildren().add(shipHpImageView);
        }
        ImageView bgView = new ImageView(bgImage);
        bgView.setFitWidth(WIDTH);
        bgView.setFitHeight(HEIGHT);
        scoreLabel = new Label("0");
        scoreLabel.getStyleClass().add("scoreLabel");
        scoreLabel.setTranslateX(10); scoreLabel.setTranslateY(10);
        specialAttackCountLbl = new Label("Bombs:"+playerShip.getSpecialAttacKCount());
        specialAttackCountLbl.getStyleClass().add("scoreLabel");
        specialAttackCountLbl.setTranslateX(10); specialAttackCountLbl.setTranslateY(80);
        hpBox.setTranslateX(10); hpBox.setTranslateY(50);
        Label countdownLabel = new Label();
        countdownLabel.getStyleClass().add("countdownLabel");
        countdownLabel.layoutXProperty().bind(this.widthProperty().subtract(countdownLabel.widthProperty()).divide(2));
        countdownLabel.layoutYProperty().bind(this.heightProperty().subtract(countdownLabel.heightProperty()).divide(2));
        getChildren().addAll(bgView, playerShip, scoreLabel,specialAttackCountLbl,countdownLabel, hpBox,bossHealthBar);
        GameStageController.onLoad(countdownLabel, this);
    }

    public PlayerShip getPlayerShip() {
        return playerShip;
    }

    public void setPlayerShip(PlayerShip playerShip) {
        this.playerShip = playerShip;
    }

    public Boss getBoss() {
        return boss;
    }

    public void setBoss(Boss boss) {
        this.boss = boss;
    }

    public HBox getHpBox() {
        return hpBox;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isBossRound() {
        return bossRound;
    }

    public void setBossRound(boolean bossRound) {
        this.bossRound = bossRound;
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

    public Label getScoreLabel() {
        return scoreLabel;
    }

    public boolean isWon() {
        return isWon;
    }

    public void setWon(boolean won) {
        this.isWon = won;
    }

    public ProgressBar getBossHealthBar() {
        return bossHealthBar;
    }
    public void updateBossHealth(int currentHealth) {
        bossHealthBar.setProgress(currentHealth/50.0);
        if (currentHealth/50.0 < 0.5) {
            bossHealthBar.setStyle(
                    "-fx-accent: red; " +
                            "-fx-background-color: black; " +
                            "-fx-border-color: white; " +
                            "-fx-border-width: 2px; " +
                            "-fx-border-radius: 5px; " +
                            "-fx-background-radius: 5px;"
            );
        }
    }

    public Label getSpecialAttackCountLbl() {
        return specialAttackCountLbl;
    }
}
