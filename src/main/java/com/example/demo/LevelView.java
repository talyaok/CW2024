package com.example.demo;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.stage.Stage;

public class LevelView {
	
	private static final double HEART_DISPLAY_X_POSITION = 5;
	private static final double HEART_DISPLAY_Y_POSITION = 25;
	private static final int WIN_IMAGE_X_POSITION = 355;
	private static final int WIN_IMAGE_Y_POSITION = 175;
	//private static final int LOSS_SCREEN_X_POSITION = 100;//-160;
	//private static final int LOSS_SCREEN_Y_POSISITION = 50;//-375;
	private final Group root;
	//private final WinImage winImage;
	//private final GameOverImage gameOverImage;
	private final HeartDisplay heartDisplay;
	//private boolean heartDisplayAdded = false; // Add a flag to track if heart display has been added


	public LevelView(Group root, int heartsToDisplay) {
		this.root = root;
		this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);

		//this.winImage = new WinImage(WIN_IMAGE_X_POSITION, WIN_IMAGE_Y_POSITION);
		//this.gameOverImage = new GameOverImage(LOSS_SCREEN_X_POSITION, LOSS_SCREEN_Y_POSISITION);
	}




	public void showHeartDisplay() {
		root.getChildren().add(heartDisplay.getContainer());
	}




	/*
	public void showHeartDisplay() {
		if (!heartDisplayAdded) { // Check if heart display has already been added
			root.getChildren().add(heartDisplay.getContainer());
			heartDisplayAdded = true; // Set the flag to true
			} }

	 */
/*
	public void showHeartDisplay() {
		if (!root.getChildren().contains(heartDisplay.getContainer())) {
			root.getChildren().add(heartDisplay.getContainer());
		}
	}

 */
/*
	public void showWinImage() {
		root.getChildren().add(winImage);
		winImage.showWinImage();
	}

 */
	/*
	public void showGameOverImage() {
		root.getChildren().add(gameOverImage);
	}

	 */
	public void showYouWonScreen(Stage stage) {
		Platform.runLater(() -> {
			YouWonScreen youWonScreen = new YouWonScreen();
			try { youWonScreen.start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			} }); }

	public void showGameOverScreen(Stage stage) {
		Platform.runLater(() -> {
			GameOverScreen gameOverScreen = new GameOverScreen();
			try {
				gameOverScreen.start(stage);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		});
	}


	public void removeHearts(int heartsRemaining) {
		int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
		for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
			heartDisplay.removeHeart();
		}
	}

}
