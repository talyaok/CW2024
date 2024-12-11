package com.example.demo;

import com.example.demo.controller.Main;
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
import javafx.stage.Stage;

public class WelcomeScreen extends Application {
    private static final int SCREEN_WIDTH = 1300;
    private static final int SCREEN_HEIGHT = 750;
    private static final String BACKGROUND_IMAGE_PATH = "/com/example/demo/images/WelcomeScreen.jpg";
    private static final String START_BUTTON_IMAGE_PATH = "/com/example/demo/images/StartButton.png";
    private static final String QUIT_BUTTON_IMAGE_PATH = "/com/example/demo/images/QuitButton.png";
    private static final String HOW_TO_PLAY_BUTTON_IMAGE_PATH = "/com/example/demo/images/HowToButton.png";


    @Override
    public void start(Stage primaryStage) {

        // Load the background image
        Image backgroundImage = new Image(getClass().getResourceAsStream(BACKGROUND_IMAGE_PATH));
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(SCREEN_WIDTH);
        backgroundImageView.setFitHeight(SCREEN_HEIGHT);

        // Load the images for the buttons
        Image startButtonImage = new Image(getClass().getResourceAsStream(START_BUTTON_IMAGE_PATH));
        ImageView startButtonImageView = new ImageView(startButtonImage);
        startButtonImageView.setFitWidth(300); // Set desired width
        startButtonImageView.setFitHeight(150); //Set desired height

        Image quitButtonImage = new Image(getClass().getResourceAsStream(QUIT_BUTTON_IMAGE_PATH));
        ImageView quitButtonImageView = new ImageView(quitButtonImage);
        quitButtonImageView.setFitWidth(200); // Set desired width
        quitButtonImageView.setFitHeight(100); // Set desired height

        Image howToPlayButtonImage = new Image(getClass().getResourceAsStream(HOW_TO_PLAY_BUTTON_IMAGE_PATH));
        ImageView howToPlayButtonImageView = new ImageView(howToPlayButtonImage);
        howToPlayButtonImageView.setFitWidth(400); // Set desired width
        howToPlayButtonImageView.setFitHeight(150); // Set desired height


        // Create the start button with the image
        Button startButton = new Button();
        startButton.setGraphic(startButtonImageView);
        startButton.setStyle("-fx-background-color: transparent;");
        startButton.setOnAction(e -> switchToGameScreen(primaryStage));
        startButton.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.SPACE) {
                event.consume();
            }
        });

        // Create the quit button with the image
        Button quitButton = new Button();
        quitButton.setGraphic(quitButtonImageView);
        quitButton.setStyle("-fx-background-color: transparent;");
        quitButton.setOnAction(e -> System.exit(0));//quit the app
        quitButton.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.SPACE) {
                event.consume();
            }
        });

        // Create the How to Play button with the image
        Button howToPlayButton = new Button();
        howToPlayButton.setGraphic(howToPlayButtonImageView);
        howToPlayButton.setStyle("-fx-background-color: transparent;");
        howToPlayButton.setOnAction(e -> showHowToPlayScreen(primaryStage));
        howToPlayButton.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.SPACE) {
                event.consume();
            }
        });
/*
        // Adjust the buttons positions
        startButton.setTranslateX(-400); // Move right by 200 pixels
        startButton.setTranslateY(-100); // Move down by 100 pixels
        quitButton.setTranslateX(-400); // Move right by 200 pixels
        quitButton.setTranslateY(-50); // Move down by 100 pixels

 */

        // Create the layout and add the background image and buttons
        StackPane layout = new StackPane();
        VBox vbox = new VBox(20); // 20 is the spacing between buttons
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(startButton, howToPlayButton, quitButton);
        layout.getChildren().addAll(backgroundImageView, vbox);

        // Create the scene with the layout, matching the game screen size
        Scene scene = new Scene(layout, SCREEN_WIDTH, SCREEN_HEIGHT);

        // Set up the stage (window) with the scene and show it
        primaryStage.setTitle("Welcome to Sky Battle");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Consume space bar event on the scene
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> { if (event.getCode() == KeyCode.SPACE) { event.consume(); } });
    }

    // Method to switch to the main game screen
    private void switchToGameScreen(Stage primaryStage) {
        try {
            Main mainApp = new Main();
            mainApp.launchGame(primaryStage); // Call the launchGame method to set up and launch the game
             }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Method to show the How to Play screen
    private void showHowToPlayScreen(Stage primaryStage) {
        HowToPlayScreen howToPlayScreen = new HowToPlayScreen();
        try {
            howToPlayScreen.start(primaryStage);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) { launch(args); }
}

