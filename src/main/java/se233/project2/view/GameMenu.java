package se233.project2.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Popup;
import se233.project2.Launcher;
import se233.project2.controller.GameMenuController;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class GameMenu extends StackPane {
    private Label titleLbl;
    private Image backgroundImg;
    private Label pointLbl;
    private Button startBtn;
    private Button helpBtn;
    private Button storeBtn;
    public static Map<String, Integer> gameDataMap = new HashMap<>();
    public MediaPlayer themePlayer;
    public GameMenu() {
        Media theme = new Media(Launcher.class.getResource("audio/GameMenuTheme.mp3").toString());
        themePlayer = new MediaPlayer(theme);
        themePlayer.setCycleCount(MediaPlayer.INDEFINITE);
        themePlayer.play();
        getStylesheets().add(Launcher.class.getResource("styles.css").toExternalForm());
        backgroundImg = new Image(Launcher.class.getResourceAsStream("menu_bg.png"));
        ImageView backgroundImgV = new ImageView(backgroundImg);
        backgroundImgV.setFitHeight(GameStage.HEIGHT);
        backgroundImgV.setFitWidth(GameStage.WIDTH);
        VBox buttonBox = new VBox();
        startBtn = new Button("Start");
        helpBtn = new Button("How To Play?");
        storeBtn = new Button("Store");
        titleLbl = new Label("Welcome To The Asteroids");
        pointLbl = new Label("Total Points: " + GameMenu.gameDataMap.get("Points"));
        buttonBox.getChildren().addAll(titleLbl, pointLbl, startBtn,helpBtn, storeBtn);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(10);
        this.getChildren().addAll(backgroundImgV,buttonBox);
        this.setAlignment(Pos.CENTER);
        styleNodes();
        initialize();
    }

    public static void saveGameDataMap(String filepath) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(filepath)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(gameDataMap);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadGameDataMap(String filepath) {
        try (FileInputStream fileInputStream = new FileInputStream(filepath)) {
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            gameDataMap = (Map<String, Integer>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            gameDataMap = new HashMap<>();
            gameDataMap.put("Points", 0);
            gameDataMap.put("HP", 3);
            gameDataMap.put("FireRate", 3);
            gameDataMap.put("BulletSpeed", 20);
            gameDataMap.put("Bomb", 3);
        }
    }

    private void styleNodes(){
        startBtn.getStyleClass().add("button");
        helpBtn.getStyleClass().add("button");
        titleLbl.getStyleClass().add("title");
        pointLbl.getStyleClass().add("scoreLabel");
    }

    public void initialize(){
        startBtn.setOnAction(e -> {
            themePlayer.stop();
            GameMenuController.onStart();
        });

        helpBtn.setOnAction(e -> {
            HowToPlayPopup popup = new HowToPlayPopup();
            popup.showHowToPlayPopup(Launcher.primaryStage);
          /*  TextArea textArea = new TextArea();
            textArea.setEditable(false);
            textArea.setWrapText(true);
            textArea.setText("This game is ......");
            popup.getContent().add(textArea);
            popup.show(this.getScene().getWindow());*/
        });

        storeBtn.setOnAction(e -> {
            Scene scene = new Scene(new StorePane());
            Launcher.primaryStage.setScene(scene);
        });
    }

}
