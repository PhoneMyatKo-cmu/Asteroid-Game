package se233.project2.view;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;

public class HowToPlayPopup {

    public void showHowToPlayPopup(Stage stage) {
        // Main layout
        VBox mainLayout = new VBox(15);
        mainLayout.setStyle("-fx-background-color: #000D1A; -fx-border-color: #00FFAA; -fx-border-width: 3px; "
                + "-fx-border-radius: 10; -fx-background-radius: 10;");
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(20));

        // Title
        Label titleLabel = new Label("How to Play");
        titleLabel.setFont(Font.font("Arial Black", 24));
        titleLabel.setTextFill(Color.LIGHTBLUE);
        titleLabel.setEffect(new DropShadow(10, Color.BLUE));
        titleLabel.setAlignment(Pos.CENTER);

        // Objective
        Label objectiveLabel = new Label("Objective: Destroy asteroids and defeat the Final Boss.");
        objectiveLabel.setTextFill(Color.LIGHTGRAY);
        objectiveLabel.setWrapText(true);
        objectiveLabel.setFont(Font.font("Arial", 16));
        objectiveLabel.setTextAlignment(TextAlignment.CENTER);

        // Controls
        Label controlsLabel = new Label("Controls:\nA W S D - Move\n🠔🠖 Rotate\nSpacebar - Shoot");
        controlsLabel.setTextFill(Color.LIGHTGRAY);
        controlsLabel.setWrapText(true);
        controlsLabel.setFont(Font.font("Arial", 16));
        controlsLabel.setTextAlignment(TextAlignment.CENTER);

        // Game Flow
        Label gameFlowLabel = new Label("Game Flow:\nStart  → Main Game → Final Boss → Game Over");
        gameFlowLabel.setTextFill(Color.LIGHTGRAY);
        gameFlowLabel.setWrapText(true);
        gameFlowLabel.setFont(Font.font("Arial", 16));
        gameFlowLabel.setTextAlignment(TextAlignment.CENTER);

        // Tips
        Label tipsLabel = new Label("Tips:\n- Stay moving\n- Use power-ups strategically\n- Prepare for the Boss!");
        tipsLabel.setTextFill(Color.LIGHTGRAY);
        tipsLabel.setWrapText(true);
        tipsLabel.setFont(Font.font("Arial", 16));
        tipsLabel.setTextAlignment(TextAlignment.CENTER);


        // Popup setup
        Popup popup = new Popup();
        popup.getContent().add(mainLayout);

        // Buttons
        Button startButton = new Button("Let's Play!");
        startButton.setStyle("-fx-background-color: #00FFAA; -fx-font-size: 18px; -fx-font-weight: bold;");
        startButton.setOnAction(e -> popup.hide());

        // Add components to layout
        mainLayout.getChildren().addAll(titleLabel, objectiveLabel, controlsLabel, gameFlowLabel, tipsLabel, startButton);
        popup.setAutoHide(true);

        // Show popup centered
        popup.show(stage);
    }
}
