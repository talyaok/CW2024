package com.example.demo;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameOverScreen extends Application {

	private static final int SCREEN_WIDTH = 1300;
	private static final int SCREEN_HEIGHT = 750;
	private static final String MENU_BUTTON_IMAGE_PATH = "/com/example/demo/images/MenuButton.png";
	private static final String QUIT_BUTTON_IMAGE_PATH = "/com/example/demo/images/QuitButton.png";
	private static final String BACKGROUND_IMAGE_PATH = "/com/example/demo/images/gameover3.png";

	@Override
	public void start(Stage primaryStage) {
		// Load the background image
		Image backgroundImage = new Image(getClass().getResourceAsStream(BACKGROUND_IMAGE_PATH));
		ImageView backgroundImageView = new ImageView(backgroundImage);
		backgroundImageView.setFitWidth(SCREEN_WIDTH);
		backgroundImageView.setFitHeight(SCREEN_HEIGHT);


		// Load the images for the buttons
		Image menuButtonImage = new Image(getClass().getResourceAsStream(MENU_BUTTON_IMAGE_PATH));
		ImageView menuButtonImageView = new ImageView(menuButtonImage);
		menuButtonImageView.setFitWidth(140); // Set desired width
		menuButtonImageView.setFitHeight(70); // Set desired height

		Image quitButtonImage = new Image(getClass().getResourceAsStream(QUIT_BUTTON_IMAGE_PATH));
		ImageView quitButtonImageView = new ImageView(quitButtonImage);
		quitButtonImageView.setFitWidth(120); // Set desired width
		quitButtonImageView.setFitHeight(60); // Set desired height


		// Create the menu button with the image
		Button menuButton = new Button();
		menuButton.setGraphic(menuButtonImageView);
		menuButton.setStyle("-fx-background-color: transparent;");
		menuButton.setOnAction(e -> showWelcomeScreen(primaryStage));

		// Create the quit button with the image
		Button quitButton = new Button();
		quitButton.setGraphic(quitButtonImageView);
		quitButton.setStyle("-fx-background-color: transparent;");
		quitButton.setOnAction(e -> System.exit(0)); // Quit the application

		// Adjust the buttons positions
		//menuButton.setTranslateX(100); // Move right by 200 pixels
		menuButton.setTranslateY(-100); // Move down by 100 pixels
		//quitButton.setTranslateX(100); // Move right by 200 pixels
		quitButton.setTranslateY(-100); // Move down by 100 pixels



		// Create the layout and add the background image, game over image, and buttons
		StackPane layout = new StackPane();
		VBox vbox = new VBox(20); // 20 is the spacing between elements
		vbox.setAlignment(Pos.BOTTOM_CENTER);
		vbox.getChildren().addAll(menuButton, quitButton);
		layout.getChildren().addAll(backgroundImageView, vbox);

		// Create the scene with the layout
		Scene scene = new Scene(layout, SCREEN_WIDTH, SCREEN_HEIGHT);

		// Set up the stage (window) with the scene and show it
		primaryStage.setTitle("Game Over");
		primaryStage.setScene(scene);
		primaryStage.show();
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
