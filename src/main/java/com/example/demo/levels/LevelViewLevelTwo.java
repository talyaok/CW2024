package com.example.demo.levels;

import com.example.demo.actors.ShieldImage;
import javafx.scene.Group;

/**
 * The LevelViewLevelTwo class is a subclass of LevelView that provides additional functionality for
 * Level Two of the game. It includes displaying a shield image that is unique to this level.
 *
 * This class adds the shield image to the visual elements of the game and provides methods to show
 * or hide the shield.
 *
 * @author Talya
 */
public class LevelViewLevelTwo extends LevelView {

	private static final int SHIELD_X_POSITION = 1150;
	private static final int SHIELD_Y_POSITION = 500;
	private final Group root;
	private final ShieldImage shieldImage;

	/**
	 * Constructs a LevelViewLevelTwo object for the given level, initializing the heart display and
	 * adding the shield image to the root container.
	 *
	 * @param root The root container where all visual elements will be added.
	 * @param heartsToDisplay The number of hearts (representing the player's health) to display.
	 */
	public LevelViewLevelTwo(Group root, int heartsToDisplay) {
		super(root, heartsToDisplay);
		this.root = root;
		this.shieldImage = new ShieldImage(SHIELD_X_POSITION, SHIELD_Y_POSITION);
		addImagesToRoot();
	}

	/**
	 * Adds the shield image to the root container. This method is called during the initialization
	 * of the level to ensure that the shield is part of the visual elements.
	 */
	private void addImagesToRoot() {
		root.getChildren().addAll(shieldImage);
	}

	/**
	 * Shows the shield image on the screen. This method makes the shield visible during the level.
	 */
	public void showShield() {
		shieldImage.showShield();
	}

	/**
	 * Hides the shield image from the screen. This method makes the shield invisible during the level.
	 */
	public void hideShield() {
		shieldImage.hideShield();
	}

}
