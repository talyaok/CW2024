package com.example.demo.actors;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Represents a shield in the game, displayed as an image. The shield is used to protect a character
 * (such as a boss) from damage. This class manages the visibility and positioning of the shield image.
 *
 * The shield can be shown or hidden, and its position can be updated to follow the character's movements.
 *
 * @author Talya
 */
public class ShieldImage extends ImageView {
	
	private static final String IMAGE_NAME = "/images/shield.png";
	private static final int SHIELD_SIZE = 100;
	private boolean isShielded = false;//d

	/**
	 * Constructs a new ShieldImage at the specified position.
	 *
	 * @param xPosition The x-coordinate where the shield will be placed.
	 * @param yPosition The y-coordinate where the shield will be placed.
	 */
	public ShieldImage(double xPosition, double yPosition) {
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
		this.setImage(new Image(getClass().getResource("/com/example/demo/images/shield.png").toExternalForm()));//jpg changed to png
		this.setVisible(true);//was false now true d
		//this.setVisible(false);
		this.setFitHeight(SHIELD_SIZE);
		this.setFitWidth(SHIELD_SIZE);
	}

	/**
	 * Activates the shield by making it visible and setting the shielded status to true.
	 * This method also prints a message indicating that the shield is active.
	 */
	public void showShield() {
		this.setVisible(true);

		if(!isShielded){//d
			isShielded = true;//d
			this.setVisible(true);
			System.out.println("Shield on (shield image) " + getLayoutX() +"," + getLayoutY());//d
		}
	}

	/**
	 * Deactivates the shield by making it invisible and printing a message.
	 */
	public void hideShield() {
		this.setVisible(false);
		System.out.println("Shield off");//d
	}

	/**
	 * Updates the position of the shield to match the specified coordinates.
	 * This method is typically used to move the shield along with its associated character.
	 *
	 * @param xPosition The new x-coordinate for the shield.
	 * @param yPosition The new y-coordinate for the shield.
	 */
	// Method to update the shield position when moving the plane(d)
	public void updatePosition(double xPosition, double yPosition) {
		setLayoutX(xPosition);
		setLayoutY(yPosition);
		System.out.println("Shield Position Updated(Shield Image class): " + getLayoutX() + ", " + getLayoutY());

	}


}
