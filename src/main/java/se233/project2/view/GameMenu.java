package se233.project2.view;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import se233.project2.Launcher;
import se233.project2.controller.GameMenuController;

import java.util.Stack;

public class GameMenu extends StackPane {
    private Label titleLbl;
    private Image backgroundImg;
    private Button startBtn;
    private Button helpBtn;
    public GameMenu() {
        getStylesheets().add(Launcher.class.getResource("styles.css").toExternalForm());
       backgroundImg = new Image(Launcher.class.getResourceAsStream("menu_bg.png"));
        ImageView backgroundImgV = new ImageView(backgroundImg);
        backgroundImgV.setFitHeight(GameStage.HEIGHT);
        backgroundImgV.setFitWidth(GameStage.WIDTH);
        VBox buttonBox = new VBox();
        startBtn = new Button("Start");
        helpBtn = new Button("How To Play?");
        titleLbl = new Label("Welcome To The Asteroids");
        buttonBox.getChildren().addAll(titleLbl,startBtn,helpBtn);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(10);
        this.getChildren().addAll(backgroundImgV,buttonBox);
        this.setAlignment(Pos.CENTER);
        styleNodes();
        initialize();
    }

    private void styleNodes(){
        startBtn.getStyleClass().add("button");
        helpBtn.getStyleClass().add("button");
        titleLbl.getStyleClass().add("title");
    }

    public void initialize(){
        startBtn.setOnAction(e -> {
            GameMenuController.onStart(startBtn);
        });

        helpBtn.setOnAction(e -> {
            Popup popup = new Popup();
            TextArea textArea = new TextArea();
            textArea.setEditable(false);
            textArea.setWrapText(true);
            textArea.setText("This game is ......");
            popup.getContent().add(textArea);
            popup.show(this.getScene().getWindow());
        });
    }

}
