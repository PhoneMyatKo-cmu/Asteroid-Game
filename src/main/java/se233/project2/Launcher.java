package se233.project2;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            Alert alert = new Alert(Alert.AlertType.NONE, throwable.getMessage(), ButtonType.OK);
            alert.setTitle("Error");
            alert.showAndWait();
        });
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
