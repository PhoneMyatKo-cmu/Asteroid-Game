package se233.project2.controller;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import se233.project2.Launcher;
import se233.project2.view.GameStage;

public class GameMenuController {
    public static void onStart(Button startBtn){
        GameStage gameStage = new GameStage();
        Scene scene=new Scene(gameStage);
        scene.setOnKeyPressed((event)->{
            gameStage.getKeys().add(event.getCode());
           // System.out.println("Game Menu:"+gameStage.keys);
        });
        scene.setOnKeyReleased((event)->{
            gameStage.getKeys().remove(event.getCode());
        });
        Launcher.primaryStage.setScene(scene );
    }





}
