package com.example.demo;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HowToPlayScreen extends Application {

    private static final int SCREEN_WIDTH = 1300;
    private static final int SCREEN_HEIGHT = 750;
    private static final String BACKGROUND_IMAGE_PATH = "/com/example/demo/images/WelcomeScreen.jpg";
    private static final String MENU_BUTTON_IMAGE_PATH = "/com/example/demo/images/MenuButton.png";

    @Override
    public void start(Stage primaryStage) {
        // Load the background image
        Image backgroundImage = new Image(getClass().getResourceAsStream(BACKGROUND_IMAGE_PATH));
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(SCREEN_WIDTH);
        backgroundImageView.setFitHeight(SCREEN_HEIGHT);

        // Create the instructions text
        Text instructions = new Text(
                "Welcome to Sky Battle!\n\n" +
                        "Instructions:\n\n" +
                        "1. Use 'Up & Down' arrow keys to move your character.\n\n" +
                        "2. Press 'Space' to shoot.\n\n" +
                        "3. Avoid enemy attacks and obstacles.\n\n" +
                        "4. Defeat the boss to win the game.\n\n" +
                        "              Good Luck Soldier!"
        );
        instructions.setStyle("-fx-fill: white; -fx-font-size: 16px; -fx-font-family: 'Arial'; -fx-font-weight: bold;");

        // Create a rectangle as a background for the text
        Rectangle textBackground = new Rectangle(800, 300); // Adjust size as needed
        textBackground.setFill(Color.rgb(0, 0, 0, 0.7)); // Semi-transparent black

        // Load the image for the menu button
        Image menuButtonImage = new Image(getClass().getResourceAsStream(MENU_BUTTON_IMAGE_PATH));
        ImageView menuButtonImageView = new ImageView(menuButtonImage);
        menuButtonImageView.setFitWidth(100); // Set desired width
        menuButtonImageView.setFitHeight(50); // Set desired height

        // Create the menu button with the image
        Button menuButton = new Button();
        menuButton.setGraphic(menuButtonImageView);
        menuButton.setStyle("-fx-background-color: transparent;");
        menuButton.setOnAction(e -> showWelcomeScreen(primaryStage));
        menuButton.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.SPACE) {
                event.consume();
            }
        });

        // Create the layout and add the background image, instructions, and button
        StackPane layout = new StackPane();
        VBox vbox = new VBox(20); // 20 is the spacing between elements
        vbox.setAlignment(Pos.CENTER);
        StackPane textPane = new StackPane(textBackground, instructions);
        vbox.getChildren().addAll(textPane, menuButton);
        layout.getChildren().addAll(backgroundImageView, vbox);

        // Create the scene with the layout
        Scene scene = new Scene(layout, SCREEN_WIDTH, SCREEN_HEIGHT);

        // Set up the stage (window) with the scene and show it
        primaryStage.setTitle("How to Play");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Consume space bar event on the scene
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> { if (event.getCode() == KeyCode.SPACE) { event.consume(); } });
    }

    // Method to show the welcome screen
    private void showWelcomeScreen(Stage primaryStage) {
        WelcomeScreen welcomeScreen = new WelcomeScreen();
        try {
            welcomeScreen.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
