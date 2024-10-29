package se233.project2.view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import se233.project2.Launcher;
import se233.project2.controller.GameMenuController;

public class GameOverMenu extends StackPane {
    public GameStage gameStage;
    public Label label;
    public Button backButton;
    public Button playAgainButton;
    private Image backgroundImage;
    private ImageView backImageView;

    public GameOverMenu( GameStage gameStage,Label label) {
        this.getStylesheets().add(Launcher.class.getResource("styles.css").toExternalForm());
        this.gameStage = gameStage;
        this.label = label;
        initialize();
    }

    public void initialize() {
        backgroundImage=new Image(Launcher.class.getResourceAsStream("bg.jpg"));
        backImageView=new ImageView(backgroundImage);
        backImageView.setFitWidth(GameStage.WIDTH);
        backImageView.setFitHeight(GameStage.HEIGHT);
        backButton=new Button("Back To Menu");
        playAgainButton=new Button("Play Again");

        this.setPrefSize(GameStage.WIDTH, GameStage.HEIGHT);
        this.getChildren().add(backImageView);
        VBox container=new VBox();
        container.getChildren().addAll(label,backButton,playAgainButton);
        container.setSpacing(15);
        container.setAlignment(Pos.CENTER);
        this.getChildren().add(container);
        this.setAlignment(Pos.CENTER);


        label.getStyleClass().add("countdownLabel");

        backButton.getStyleClass().add("button");
        playAgainButton.getStyleClass().add("button");

      /*  if(gameState==GameState.VICTORY){
            playAgainButton.setDisable(true);
            playAgainButton.setVisible(false);

        }*/

        backButton.setOnAction(event->{
            Scene scene=new Scene(new GameMenu());
            scene.getStylesheets().add(Launcher.class.getResource("styles.css").toExternalForm());
           // GameStage.gameState=GameState.ASTEROID;
            Launcher.primaryStage.setScene(scene);
        });

        playAgainButton.setOnAction(event-> GameMenuController.onStart());

        backButton.setFocusTraversable(false);
        playAgainButton.setFocusTraversable(false);
    }
}
