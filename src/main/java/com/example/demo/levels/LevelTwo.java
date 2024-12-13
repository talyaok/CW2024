package com.example.demo.levels;

import com.example.demo.LevelParent;
import com.example.demo.actors.PowerUp;
import com.example.demo.actors.Boss;
import javafx.stage.Stage;
import javafx.scene.Scene; // Import Scene class

/**
 * The LevelTwo class represents the second level of the game. This level introduces a boss fight,
 * where the player must defeat a boss while maintaining their health. It also includes the spawning
 * of a power-up to assist the player. The game is won if the boss is destroyed, and it is lost if
 * the player is destroyed.
 *
 * This class extends {@link LevelParent} and overrides several methods to initialize the boss,
 * power-ups, and handle level completion conditions.
 *
 * @author Talya
 */
public class LevelTwo extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/bglevel2.png";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private final Boss boss;
	private LevelViewLevelTwo levelView;
	private final Stage primaryStage;
	private PowerUp powerUp; // Add PowerUp field

	/**
	 * Constructs a LevelTwo object, initializing the background, the player’s initial health, and
	 * the boss.
	 *
	 * @param screenHeight The height of the screen for layout purposes.
	 * @param screenWidth The width of the screen for layout purposes.
	 * @param primaryStage The primary stage for displaying the game.
	 */
	public LevelTwo(double screenHeight, double screenWidth, Stage primaryStage) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
		this.primaryStage = primaryStage;
		boss = new Boss();
		System.out.println("LevelTwo initialized with user health: " + getUser().getHealth());
	}

	/**
	 * Initializes the friendly units in the level, including adding the user-controlled plane and
	 * the boss's shield to the root container.
	 */
	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
		System.out.println("Friendly units initialized in LevelTwo.");
		getRoot().getChildren().add(boss.getShieldImage());//d
	}

	/**
	 * Initializes the containers for the level, including the display for the boss's health.
	 */
	@Override
	protected void initializeContainers() {
		initializeBossLivesDisplay(boss);
	}


	/**
	 * Checks if the game is over. If the user is destroyed, the game ends. If the boss is destroyed,
	 * the player wins the game.
	 */
	@Override
	protected void checkIfGameOver() {
		System.out.println("Checking if game is over...");
		System.out.println("User health: " + getUser().getHealth());
		System.out.println("Boss health: " + boss.getHealth());

		if (userIsDestroyed()) {
			System.out.println("User is destroyed, losing game.");
			loseGame(primaryStage);
		} else if (boss.isDestroyed()) {
			System.out.println("Boss is destroyed, winning game.");
			winGame(primaryStage);
		}
	}

	/**
	 * Spawns enemy units for the level. In this case, the boss is the only enemy unit, and it is
	 * spawned when no other enemies are present.
	 */
	@Override
	protected void spawnEnemyUnits() {
		if (getCurrentNumberOfEnemies() == 0) {
			addEnemyUnit(boss);
			System.out.println("Boss added to the game.");
		}
	}

	/**
	 * Instantiates the level view, which represents the visual elements of the level. It also shows
	 * the boss's shield.
	 *
	 * @return The instantiated level view.
	 */
	@Override
	protected LevelView instantiateLevelView() {
		levelView = new LevelViewLevelTwo(getRoot(), PLAYER_INITIAL_HEALTH);
		levelView.showShield();//d
		System.out.println("LevelViewLevelTwo instantiated.");
		return levelView;
	}

	/**
	 * Spawns a power-up in the level. This power-up helps the player by providing boosts or benefits
	 * during the game.
	 */
	// Add method to spawn power-up
	private void spawnPowerUp() {
		powerUp = new PowerUp(this);
		getRoot().getChildren().add(powerUp);
	}

	/**
	 * Initializes the scene for the level, including the spawning of the power-up. This method is
	 * overridden to ensure that the power-up is part of the scene from the start.
	 *
	 * @return The initialized scene with the power-up.
	 */
	// Override initializeScene to include power-up initialization
	@Override
	public Scene initializeScene() {
		Scene scene = super.initializeScene();
		spawnPowerUp();
		return scene;
	}

}
