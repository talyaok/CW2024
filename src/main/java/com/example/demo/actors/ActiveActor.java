package com.example.demo.actors;

import javafx.scene.image.*;

/**
 * Represents an active actor in the Sky Battle Game with the ability to change positions.
 * This abstract class serves as a base for all actors that can move and interact in the game.
 * Subclasses must define the specific behavior for updating their positions.
 *
 * @author Talya
 */

public abstract class ActiveActor extends ImageView {
	/**
	 * The base path for loading images representing the actors.
	 */
	private static final String IMAGE_LOCATION = "/com/example/demo/images/";

	/**
	 * Creates an instance of an ActiveActor with a specified image, size, and initial position.
	 *
	 * @param imageName The name of the image file representing the ActiveActor.
	 * @param imageHeight The height of the ActiveActor's image in pixels. The width is scaled proportionally.
	 * @param initialXPos The initial x-coordinate of the ActiveActor.
	 * @param initialYPos The initial y-coordinate of the ActiveActor.
	 */
	public ActiveActor(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		this.setImage(new Image(getClass().getResource(IMAGE_LOCATION + imageName).toExternalForm()));
		this.setLayoutX(initialXPos);
		this.setLayoutY(initialYPos);
		this.setFitHeight(imageHeight);
		this.setPreserveRatio(true);
	}
	/**
	 * Updates the position of the ActiveActor.
	 * This method must be implemented by subclasses to define specific movement behavior.
	 */
	public abstract void updatePosition();
	/**
	 * Moves the ActiveActor horizontally by a specified amount.
	 *
	 * @param horizontalMove The distance to move the ActiveActor horizontally, in pixels.
	 *                       Positive values move to the right; negative values move to the left.
	 */
	protected void moveHorizontally(double horizontalMove) {
		this.setTranslateX(getTranslateX() + horizontalMove);
	}
	/**
	 * Moves the ActiveActor vertically by a specified amount.
	 *
	 * @param verticalMove The distance to move the ActiveActor vertically, in pixels.
	 *                     Positive values move down; negative values move up.
	 */
	protected void moveVertically(double verticalMove) {
		this.setTranslateY(getTranslateY() + verticalMove);
	}

}
