package com.example.demo.displays;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * The HeartDisplay class is responsible for displaying a set of hearts in the game,
 * typically representing the player's remaining health or lives. It allows for hearts to
 * be removed as the player loses health.
 *
 * This class initializes a container to hold heart images, and provides methods to remove
 * hearts when the player takes damage.
 *
 * @author Talya
 */
public class HeartDisplay {
	
	private static final String HEART_IMAGE_NAME = "/com/example/demo/images/heart.png";
	private static final int HEART_HEIGHT = 50;
	private static final int INDEX_OF_FIRST_ITEM = 0;
	private HBox container;
	private double containerXPosition;
	private double containerYPosition;
	private int numberOfHeartsToDisplay;

	/**
	 * Constructs a HeartDisplay object to initialize and display the hearts container.
	 *
	 * @param xPosition The horizontal position of the heart display on the screen.
	 * @param yPosition The vertical position of the heart display on the screen.
	 * @param heartsToDisplay The initial number of hearts to be displayed.
	 */
	public HeartDisplay(double xPosition, double yPosition, int heartsToDisplay) {
		this.containerXPosition = xPosition;
		this.containerYPosition = yPosition;
		this.numberOfHeartsToDisplay = heartsToDisplay;
		initializeContainer();
		initializeHearts();
	}

	/**
	 * Initializes the container (HBox) to hold the heart images.
	 */
	private void initializeContainer() {
		container = new HBox();
		container.setLayoutX(containerXPosition);
		container.setLayoutY(containerYPosition);		
	}

	/**
	 * Initializes the heart images and adds them to the container.
	 * This method will create a number of heart images based on the number of hearts to display.
	 */
	private void initializeHearts() {
		for (int i = 0; i < numberOfHeartsToDisplay; i++) {
			ImageView heart = new ImageView(new Image(getClass().getResource(HEART_IMAGE_NAME).toExternalForm()));

			heart.setFitHeight(HEART_HEIGHT);
			heart.setPreserveRatio(true);
			container.getChildren().add(heart);
		}
	}

	/**
	 * Removes one heart from the display when the player loses health.
	 * If there are no hearts remaining, this method does nothing.
	 */
	public void removeHeart() {
		if (!container.getChildren().isEmpty())
			container.getChildren().remove(INDEX_OF_FIRST_ITEM);
	}

	/**
	 * Returns the container (HBox) holding the heart images.
	 *
	 * @return The HBox container holding the heart images.
	 */
	public HBox getContainer() {
		return container;
	}

	/**
	 * Handles the game over scenario when the player runs out of hearts.
	 * This method is called when the player loses all their hearts.
	 */
	public void gameOver() {
		System.out.println("Game Over! You have lost all your hearts.");
	}

}
