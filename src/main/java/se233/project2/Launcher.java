package se233.project2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import se233.project2.controller.DrawingLoop;
import se233.project2.controller.GameLoop;
import se233.project2.controller.GenerateAsteroidTask;
import se233.project2.view.GameStage;

public class Launcher extends Application {
    private GameStage gameStage;

    @Override
    public void start(Stage primaryStage) {
        gameStage = new GameStage();
        Scene scene = new Scene(gameStage);
        scene.setOnKeyPressed(e -> gameStage.getKeys().add(e.getCode()));
        scene.setOnKeyReleased(e -> gameStage.getKeys().remove(e.getCode()));
        primaryStage.setScene(scene);
        primaryStage.setTitle("Asteroid Game");
        primaryStage.setResizable(false);
        primaryStage.show();
        new Thread(new GameLoop(gameStage)).start();
        new Thread(new DrawingLoop(gameStage)).start();
        new Thread(new GenerateAsteroidTask(gameStage)).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
