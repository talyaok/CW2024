package com.example.demo.levels;

import com.example.demo.actors.EnemyPlane;
import com.example.demo.LevelParent;
import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.menus.TransitionScreen;
import javafx.stage.Stage;

/**
 * The LevelOne class represents the first level in the game. It handles the setup of the level,
 * including the background, spawning of enemies, and tracking the player's progress. The player
 * needs to defeat a specific number of enemies to advance to the next level. If the player fails,
 * they lose the game.
 *
 * This class extends {@link LevelParent} and overrides methods to initialize the level, spawn
 * enemies, and check for level completion conditions.
 *
 * @author Talya
 */
public class LevelOne extends LevelParent {
	
	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/bglevel1.jpg";
	private static final String NEXT_LEVEL = "com.example.demo.levels.LevelTwo";
	private static final int TOTAL_ENEMIES = 10;
	private static final int KILLS_TO_ADVANCE = 10;
	private static final double ENEMY_SPAWN_PROBABILITY = .20;
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private boolean checkLevelTransition = false;
	private final Stage primaryStage;

	/**
	 * Constructs a LevelOne object, initializing the background and the player’s initial health.
	 *
	 * @param screenHeight The height of the screen for layout purposes.
	 * @param screenWidth The width of the screen for layout purposes.
	 * @param primaryStage The primary stage for displaying the game.
	 */
	public LevelOne(double screenHeight, double screenWidth, Stage primaryStage) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
		this.primaryStage = primaryStage;

	}

	/**
	 * Initializes the containers for the level, including the display for the number of planes left.
	 */
	@Override
	protected void initializeContainers() {
		initializePlanesLeftDisplay(TOTAL_ENEMIES); //new
	}

	/**
	 * Checks if the game is over. If the player is destroyed, the game ends. If the player reaches
	 * the target number of kills, the level is marked as complete and the transition screen is shown.
	 */
	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame(primaryStage);
		}
		else if (userHasReachedKillTarget() && !checkLevelTransition){//d
			checkLevelTransition = true;//d
			System.out.println("level 1 complete");//d
			goToTransitionScreen();
		}

	}

	/**
	 * Initializes the friendly units in the level, including adding the user-controlled plane.
	 */
	@Override
	protected void initializeFriendlyUnits() {

		getRoot().getChildren().add(getUser());

	}


	/**
	 * Spawns enemy units at random positions based on the defined spawn probability.
	 * The number of enemies spawned is based on the total number of enemies required.
	 */
	@Override
	protected void spawnEnemyUnits() {
		int currentNumberOfEnemies = getCurrentNumberOfEnemies();
		for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
			if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
				double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
				ActiveActorDestructible newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition);
				addEnemyUnit(newEnemy);
			}
		}
	}

	/**
	 * Instantiates the level view, which represents the visual elements of the level.
	 *
	 * @return The instantiated level view.
	 */
	@Override
	protected LevelView instantiateLevelView() {
		return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
	}

	/**
	 * Displays the transition screen when the player completes the level.
	 * This screen gives the player a chance to proceed to the next level.
	 */
	private void goToTransitionScreen() {
		TransitionScreen transitionScreen = new TransitionScreen(primaryStage);
		transitionScreen.display();
	}

	/**
	 * Checks if the player has reached the target number of kills required to advance to the next level.
	 *
	 * @return True if the player has reached the kill target, false otherwise.
	 */
	private boolean userHasReachedKillTarget() {
		return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
	}

}
