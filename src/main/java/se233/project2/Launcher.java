package se233.project2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import se233.project2.view.GameMenu;

import java.io.File;

public class Launcher extends Application {
    private GameMenu gameMenu;
    public static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        GameMenu.loadGameDataMap("GameData.ser");
        gameMenu = new GameMenu();
        Scene scene = new Scene(gameMenu);
        primaryStage = stage;
        primaryStage.setOnCloseRequest(e -> gameMenu.saveGameDataMap("GameData.ser"));
        primaryStage.setScene(scene);
        primaryStage.setTitle("Asteroid Game");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
