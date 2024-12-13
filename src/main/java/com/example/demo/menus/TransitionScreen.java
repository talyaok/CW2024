package com.example.demo.menus;

import com.example.demo.levels.LevelTwo;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;

/**
 * The TransitionScreen class represents the screen that appears after completing a level,
 * allowing the player to navigate to the next level, the main menu, or quit the game.
 * It includes buttons for transitioning to Level Two, returning to the welcome screen, and quitting the game.
 * The background and button images are loaded dynamically, and actions are triggered by button clicks or keyboard events.
 *
 * @author Talya
 */
public class TransitionScreen {
    private static final int SCREEN_WIDTH = 1300;
    private static final int SCREEN_HEIGHT = 750;
    private static final String BACKGROUND_IMAGE_PATH = "/com/example/demo/images/TransitionScreen.png";
    private static final String LEVEL2_BUTTON_IMAGE_PATH = "/com/example/demo/images/NextLevelButton.png";
    private static final String MAIN_MENU_BUTTON_IMAGE_PATH = "/com/example/demo/images/MenuButton.png";
    private static final String QUIT_BUTTON_IMAGE_PATH = "/com/example/demo/images/QuitButton.png";

    private Stage primaryStage;

    /**
     * Constructor for the TransitionScreen class.
     *
     * @param primaryStage The primary stage (window) of the application.
     */
    public TransitionScreen(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Displays the transition screen, which includes a background image,
     * buttons for transitioning to Level 2, returning to the main menu,
     * and quitting the game. Button events are set up to handle each action.
     */
    public void display() {
        // Load the background image
        Image backgroundImage = new Image(getClass().getResourceAsStream(BACKGROUND_IMAGE_PATH));
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(SCREEN_WIDTH);
        backgroundImageView.setFitHeight(SCREEN_HEIGHT);

        // Load the images for the buttons
        Image level2ButtonImage = new Image(getClass().getResourceAsStream(LEVEL2_BUTTON_IMAGE_PATH));
        ImageView level2ButtonImageView = new ImageView(level2ButtonImage);
        level2ButtonImageView.setFitWidth(300); // Set desired width
        level2ButtonImageView.setFitHeight(100); // Set desired height

        Image mainMenuButtonImage = new Image(getClass().getResourceAsStream(MAIN_MENU_BUTTON_IMAGE_PATH));
        ImageView mainMenuButtonImageView = new ImageView(mainMenuButtonImage);
        mainMenuButtonImageView.setFitWidth(200); // Set desired width
        mainMenuButtonImageView.setFitHeight(100); // Set desired height

        Image quitButtonImage = new Image(getClass().getResourceAsStream(QUIT_BUTTON_IMAGE_PATH));
        ImageView quitButtonImageView = new ImageView(quitButtonImage);
        quitButtonImageView.setFitWidth(200); // Set desired width
        quitButtonImageView.setFitHeight(100); // Set desired height

        // Create the buttons with images
        Button level2Button = new Button();
        level2Button.setGraphic(level2ButtonImageView);
        level2Button.setStyle("-fx-background-color: transparent;");
        level2Button.setOnAction(event -> goToLevelTwo());
        level2Button.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.SPACE) {
                event.consume();
            }
        });

        Button mainMenuButton = new Button();
        mainMenuButton.setGraphic(mainMenuButtonImageView);
        mainMenuButton.setStyle("-fx-background-color: transparent;");
        mainMenuButton.setOnAction(event -> showWelcomeScreen(primaryStage));
        mainMenuButton.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.SPACE) {
                event.consume();
            }
        });

        Button quitButton = new Button();
        quitButton.setGraphic(quitButtonImageView);
        quitButton.setStyle("-fx-background-color: transparent;");
        quitButton.setOnAction(event -> quitGame());
        quitButton.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.SPACE) {
                event.consume();
            }
        });

        // Create the layout and add components
        VBox layout = new VBox(20);
        layout.getChildren().addAll(level2Button, mainMenuButton, quitButton);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: transparent;");

        // Stack background and layout
        StackPane root = new StackPane();
        root.getChildren().addAll(backgroundImageView, layout);

        // Create scene and set it on the stage
        Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Consume space bar event on the scene
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> { if (event.getCode() == KeyCode.SPACE) { event.consume(); } });
    }

    /**
     * Navigates to Level 2 when the corresponding button is clicked.
     * Initializes the LevelTwo scene and starts the game.
     */
    private void goToLevelTwo() {
        LevelTwo levelTwo = new LevelTwo(750, 1300, primaryStage); // Adjust screen size if necessary
        Scene levelTwoScene = levelTwo.initializeScene();
        primaryStage.setScene(levelTwoScene);
        levelTwo.startGame();
    }

    /**
     * Displays the welcome screen when the main menu button is clicked.
     *
     * @param primaryStage The primary stage (window) for the application.
     */
    private void showWelcomeScreen(Stage primaryStage) {
        WelcomeScreen welcomeScreen = new WelcomeScreen();
        try {
            welcomeScreen.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Quits the game when the quit button is clicked.
     */
    private void quitGame() {
        primaryStage.close();
    }
}
