package com.example.demo.levels;

import com.example.demo.displays.HeartDisplay;
import com.example.demo.menus.GameOverScreen;
import com.example.demo.menus.YouWonScreen;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.stage.Stage;

/**
 * The LevelView class is responsible for managing the visual representation of the game level. It
 * handles displaying the player's health through heart icons, as well as showing game over or victory
 * screens when necessary.
 *
 * This class works in conjunction with the game level to display relevant information to the player
 * during gameplay.
 *
 * @author Talya
 */
public class LevelView {
	
	private static final double HEART_DISPLAY_X_POSITION = 5;
	private static final double HEART_DISPLAY_Y_POSITION = 25;

	private final Group root;
	private final HeartDisplay heartDisplay;


	/**
	 * Constructs a LevelView object for the given level. Initializes the heart display and sets the
	 * root container.
	 *
	 * @param root The root container where all visual elements will be added.
	 * @param heartsToDisplay The number of hearts (representing the player's health) to display.
	 */
	public LevelView(Group root, int heartsToDisplay) {
		this.root = root;
		this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, heartsToDisplay);
	}


	/**
	 * Displays the heart display on the screen. This shows the player's current health using heart icons.
	 */
	public void showHeartDisplay() {
		root.getChildren().add(heartDisplay.getContainer());
	}


	/**
	 * Shows the "You Won!" screen when the player wins the game. This screen is displayed asynchronously
	 * on the JavaFX application thread.
	 *
	 * @param stage The stage on which to display the "You Won!" screen.
	 */
	public void showYouWonScreen(Stage stage) {
		Platform.runLater(() -> {
			YouWonScreen youWonScreen = new YouWonScreen();
			try { youWonScreen.start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			} }); }

	/**
	 * Shows the "Game Over" screen when the player loses the game. This screen is displayed asynchronously
	 * on the JavaFX application thread.
	 *
	 * @param stage The stage on which to display the "Game Over" screen.
	 */
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


	/**
	 * Removes hearts from the heart display to reflect the remaining health of the player.
	 *
	 * @param heartsRemaining The number of hearts to display after removing some.
	 */
	public void removeHearts(int heartsRemaining) {
		int currentNumberOfHearts = heartDisplay.getContainer().getChildren().size();
		for (int i = 0; i < currentNumberOfHearts - heartsRemaining; i++) {
			heartDisplay.removeHeart();
		}
	}

}
