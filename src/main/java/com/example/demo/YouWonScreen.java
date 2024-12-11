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
import javafx.stage.Stage;

public class YouWonScreen extends Application {

    private static final int SCREEN_WIDTH = 1300;
    private static final int SCREEN_HEIGHT = 750;
    private static final String BACKGROUND_IMAGE_PATH = "/com/example/demo/images/youwon.png";
    private static final String QUIT_BUTTON_IMAGE_PATH = "/com/example/demo/images/QuitButton.png";
    private static final String MENU_BUTTON_IMAGE_PATH = "/com/example/demo/images/MenuButton.png";

    @Override
    public void start(Stage primaryStage) {
        // Load the background image
        Image backgroundImage = new Image(getClass().getResourceAsStream(BACKGROUND_IMAGE_PATH));
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(SCREEN_WIDTH);
        backgroundImageView.setFitHeight(SCREEN_HEIGHT);


        Image quitButtonImage = new Image(getClass().getResourceAsStream(QUIT_BUTTON_IMAGE_PATH));
        ImageView quitButtonImageView = new ImageView(quitButtonImage);
        quitButtonImageView.setFitWidth(200); // Set desired width
        quitButtonImageView.setFitHeight(100); // Set desired height

        Image menuButtonImage = new Image(getClass().getResourceAsStream(MENU_BUTTON_IMAGE_PATH));
        ImageView menuButtonImageView = new ImageView(menuButtonImage);
        menuButtonImageView.setFitWidth(200); // Set desired width
        menuButtonImageView.setFitHeight(100); // Set desired height


        // Create the quit button with the image
        Button quitButton = new Button();
        quitButton.setGraphic(quitButtonImageView);
        quitButton.setStyle("-fx-background-color: transparent;");
        quitButton.setOnAction(e -> System.exit(0)); // Quit the application
        quitButton.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.SPACE) {
                event.consume();
            }
        });

        // Create the menu button with the image
        Button menuButton = new Button();
        menuButton.setGraphic(menuButtonImageView);
        menuButton.setStyle("-fx-background-color: transparent;");
        menuButton.setOnAction(e -> showMenuScreen(primaryStage));
        menuButton.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.SPACE) {
                event.consume();
            }
        });

        // Create the layout and add the background image and buttons
        StackPane layout = new StackPane();
        VBox vbox = new VBox(20); // 20 is the spacing between buttons
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(menuButton, quitButton);
        layout.getChildren().addAll(backgroundImageView, vbox);

        // Create the scene with the layout, matching the game screen size
        Scene scene = new Scene(layout, SCREEN_WIDTH, SCREEN_HEIGHT);

        // Set up the stage (window) with the scene and show it
        primaryStage.setTitle("You Won!");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Consume space bar event on the scene
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> { if (event.getCode() == KeyCode.SPACE) { event.consume(); } });
    }


    private void showMenuScreen(Stage primaryStage) {
        try {
            WelcomeScreen welcomeScreen = new WelcomeScreen();
            welcomeScreen.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

