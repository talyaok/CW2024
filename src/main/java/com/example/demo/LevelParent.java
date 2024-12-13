package com.example.demo;

import java.util.*;
import java.util.stream.Collectors;

import com.example.demo.actors.ActiveActorDestructible;
import com.example.demo.actors.Boss;
import com.example.demo.actors.FighterPlane;
import com.example.demo.actors.UserPlane;
import com.example.demo.displays.BossLivesDisplay;
import com.example.demo.displays.PlanesLeftDisplay;
import com.example.demo.levels.LevelView;
import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * The LevelParent class is the base class for all game levels.
 * It handles core game mechanics, including actor management, collision detection,
 * game state transitions, and visual updates.
 * <p>
 * This class manages the user plane, enemy units, projectiles, and other game objects.
 * It defines the game loop, handling the core functionality needed for any level.
 * Subclasses implement level-specific behavior such as spawning enemies or displaying level details.
 * </p>
 *
 * @author Talya
 */
public abstract class LevelParent extends Observable {

	private static final double SCREEN_HEIGHT_ADJUSTMENT = 150;
	private static final int MILLISECOND_DELAY = 50;
	private final double screenHeight;
	private final double screenWidth;
	private final double enemyMaximumYPosition;

	private final Group root;
	private final Timeline timeline;
	private final UserPlane user;
	private final Scene scene;
	private final ImageView background;

	protected final List<ActiveActorDestructible> friendlyUnits;
	private final List<ActiveActorDestructible> enemyUnits;
	private final List<ActiveActorDestructible> userProjectiles;
	private final List<ActiveActorDestructible> enemyProjectiles;
	
	private int currentNumberOfEnemies;
	private LevelView levelView;
	private PlanesLeftDisplay planesLeftDisplay; // Add PlanesLeftDisplay field
	private BossLivesDisplay bossLivesDisplay; // Add BossLivesDisplay field

	/**
	 * Constructs a new level with the specified background image, screen dimensions,
	 * and initial player health.
	 *
	 * @param backgroundImageName The background image for the level.
	 * @param screenHeight The height of the screen for the level.
	 * @param screenWidth The width of the screen for the level.
	 * @param playerInitialHealth The initial health of the player.
	 */
	public LevelParent(String backgroundImageName, double screenHeight, double screenWidth, int playerInitialHealth) {
		this.root = new Group();
		this.scene = new Scene(root, screenWidth, screenHeight);
		this.timeline = new Timeline();
		this.user = new UserPlane(playerInitialHealth);
		this.friendlyUnits = new ArrayList<>();
		this.enemyUnits = new ArrayList<>();
		this.userProjectiles = new ArrayList<>();
		this.enemyProjectiles = new ArrayList<>();

		this.background = new ImageView(new Image(getClass().getResource(backgroundImageName).toExternalForm()));
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.enemyMaximumYPosition = screenHeight - SCREEN_HEIGHT_ADJUSTMENT;
		this.levelView = instantiateLevelView();
		this.currentNumberOfEnemies = 0;
		initializeTimeline();
		friendlyUnits.add(user);


	}

	/**
	 * Gets the list of user projectiles currently in the game.
	 *
	 * @return The list of user projectiles.
	 */
	// Getter for userProjectiles
	public List<ActiveActorDestructible> getUserProjectiles() {
		return userProjectiles;
	}


	/**
	 * Initializes the display that shows the number of planes (enemies) remaining in the game.
	 *
	 * @param totalEnemies The total number of enemy units at the start of the level.
	 */
	// Initialize planes left display
	protected void initializePlanesLeftDisplay(int totalEnemies) {
		planesLeftDisplay = new PlanesLeftDisplay(root, screenWidth, 25, user, totalEnemies);
		planesLeftDisplay.start();
	}

	/**
	 * Initializes the display that shows the remaining lives of the boss.
	 *
	 * @param boss The boss whose remaining health will be displayed.
	 */
	// Initialize boss lives container
	protected void initializeBossLivesDisplay(Boss boss) {
		bossLivesDisplay = new BossLivesDisplay(root, screenWidth, 25, boss);
		bossLivesDisplay.start();
	}


	/**
	 * Abstract method to initialize the friendly units (e.g., the player and allies).
	 * This must be implemented by subclasses.
	 */
	protected abstract void initializeFriendlyUnits();

	/**
	 * Abstract method to initialize containers for game objects (e.g., projectiles, enemies).
	 * This must be implemented by subclasses.
	 */
	protected abstract void initializeContainers(); // Add this method to be overridden by subclasses

	/**
	 * Abstract method to check whether the game is over based on certain conditions (e.g., player death).
	 * This must be implemented by subclasses.
	 */
	protected abstract void checkIfGameOver();

	/**
	 * Abstract method to spawn enemy units for the level.
	 * This must be implemented by subclasses.
	 */
	protected abstract void spawnEnemyUnits();

	/**
	 * Abstract method to instantiate and return the level view, which controls the visual display of the level.
	 *
	 * @return The level view object.
	 */
	protected abstract LevelView instantiateLevelView();

	/**
	 * Initializes the scene for the level by setting up the background, friendly units, and other necessary containers.
	 *
	 * @return The scene object for the level.
	 */
	public Scene initializeScene() {
		initializeBackground();
		initializeFriendlyUnits();
		initializeContainers(); // Initialize containers in subclasses
		levelView.showHeartDisplay();
		return scene;
	}



	/**
	 * Starts the game by starting the animation timeline, which begins updating the game state.
	 */
	public void startGame() {
		background.requestFocus();
		timeline.play();
		System.out.println("Game started and timeline playing.");
		 	}


	/**
	 * Transitions to the next level by notifying observers of the change.
	 *
	 * @param levelName The name of the next level.
	 */
	public void goToNextLevel(String levelName) {
		System.out.println("Transitioning to next level: " + levelName);
		setChanged();
		notifyObservers(levelName);
	}

	/**
	 * Updates the game state, including spawning enemies, updating actors, checking collisions,
	 * and removing destroyed actors.
	 */
	private void updateScene() {
		spawnEnemyUnits();
		updateActors();
		generateEnemyFire();
		updateNumberOfEnemies();
		handleEnemyPenetration();
		handleUserProjectileCollisions();
		handleEnemyProjectileCollisions();
		handlePlaneCollisions();
		removeAllDestroyedActors();
		updateKillCount();
		updateLevelView();
		checkIfGameOver();
	}

	/**
	 * Initializes the timeline for the game loop, which updates the game state at regular intervals.
	 */
	private void initializeTimeline() {
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene());
		timeline.getKeyFrames().add(gameLoop);
		System.out.println("Timeline initialized.");
	}

	/**
	 * Initializes the background image for the level and sets up key event handlers for user input.
	 */
	private void initializeBackground() {
		background.setFocusTraversable(true);
		background.setFitHeight(screenHeight);
		background.setFitWidth(screenWidth);
		background.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				KeyCode kc = e.getCode();
				if (kc == KeyCode.UP) user.moveUp();
				if (kc == KeyCode.DOWN) user.moveDown();
				if (kc == KeyCode.SPACE) fireProjectile();
				e.consume(); // Prevent further handling of the event
			}
		});
		background.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent e) {
				KeyCode kc = e.getCode();
				if (kc == KeyCode.UP || kc == KeyCode.DOWN) user.stop();
				e.consume(); // Prevent further handling of the event
			}
		});
		root.getChildren().add(background);
		System.out.println("Background initialized.");
	}

	/**
	 * Fires a projectile from the user plane and adds it to the scene.
	 */
	private void fireProjectile() {
		ActiveActorDestructible projectile = user.fireProjectile();
		root.getChildren().add(projectile);
		userProjectiles.add(projectile);
	}

	/**
	 * Generates enemy projectiles by having each enemy fire one.
	 */
	private void generateEnemyFire() {
		enemyUnits.forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile()));
	}

	/**
	 * Spawns an enemy projectile and adds it to the scene.
	 *
	 * @param projectile The projectile to be spawned.
	 */
	private void spawnEnemyProjectile(ActiveActorDestructible projectile) {
		if (projectile != null) {
			root.getChildren().add(projectile);
			enemyProjectiles.add(projectile);
			System.out.println("Enemy fired a projectile.");
		}
	}

	/**
	 * Updates all actors in the game (planes, projectiles) to their next state.
	 */

	private void updateActors() {
		friendlyUnits.forEach(plane -> plane.updateActor());
		enemyUnits.forEach(enemy -> enemy.updateActor());
		userProjectiles.forEach(projectile -> projectile.updateActor());
		enemyProjectiles.forEach(projectile -> projectile.updateActor());
	}

	/**
	 * Removes all destroyed actors from the scene and from the corresponding lists.
	 */
	private void removeAllDestroyedActors() {
		removeDestroyedActors(friendlyUnits);
		removeDestroyedActors(enemyUnits);
		removeDestroyedActors(userProjectiles);
		removeDestroyedActors(enemyProjectiles);
	}

	/**
	 * Removes all destroyed actors from the given list and the scene.
	 *
	 * @param actors The list of actors to check for destruction.
	 */
	private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
		List<ActiveActorDestructible> destroyedActors = actors.stream().filter(actor -> actor.isDestroyed())
				.collect(Collectors.toList());
		root.getChildren().removeAll(destroyedActors);
		actors.removeAll(destroyedActors);
	}

	/**
	 * Handles collisions between planes (both friendly and enemy).
	 */
	private void handlePlaneCollisions() {
		handleCollisions(friendlyUnits, enemyUnits);
	}

	/**
	 * Handles collisions between user projectiles and enemy units.
	 */
	private void handleUserProjectileCollisions() {
		handleCollisions(userProjectiles, enemyUnits);
	}

	/**
	 * Handles collisions between enemy projectiles and friendly units (including the user plane).
	 */
	private void handleEnemyProjectileCollisions() {
		handleCollisions(enemyProjectiles, friendlyUnits);
	}


	/**
	 * Handles collisions between two lists of actors and applies damage to both actors in each collision.
	 * If the user projectile hits an enemy, the kill count is incremented.
	 *
	 * @param actors1 The first list of actors.
	 * @param actors2 The second list of actors.
	 */
	private void handleCollisions(List<ActiveActorDestructible> actors1,
								  List<ActiveActorDestructible> actors2) {
		for (ActiveActorDestructible actor : actors2) {
			for (ActiveActorDestructible otherActor : actors1) {
				if (actor.getBoundsInParent().intersects(otherActor.getBoundsInParent())) {
					actor.takeDamage();
					otherActor.takeDamage();
					if (!actor.isDestroyed() && !otherActor.isDestroyed()) {
						// Increment kill count and update planes left text if user projectile hits enemy plane
						if (actors1 == userProjectiles && actors2 == enemyUnits) {
							getUser().incrementKillCount();
						}
						System.out.println("Collision detected between " + actor + " and " + otherActor); // Debug
					}
				}
			}
		}
	}



	/**
	 * Checks if any enemy unit has penetrated the user's defenses (by passing beyond the left edge of the screen).
	 */
	private void handleEnemyPenetration() {
		for (ActiveActorDestructible enemy : enemyUnits) {
			if (enemyHasPenetratedDefenses(enemy)) {
				user.takeDamage();
				enemy.destroy();
			}
		}
	}

	/**
	 * Updates the level view by removing hearts based on the user's health.
	 */
	private void updateLevelView() {
		levelView.removeHearts(user.getHealth());
	}

	/**
	 * Updates the kill count based on the number of enemies destroyed since the last update.
	 */
	private void updateKillCount() {
		for (int i = 0; i < currentNumberOfEnemies - enemyUnits.size(); i++) {
			user.incrementKillCount();
		}
	}

	/**
	 * Checks if an enemy has penetrated the user's defenses.
	 *
	 * @param enemy The enemy to check.
	 * @return True if the enemy has passed beyond the left edge of the screen, false otherwise.
	 */
	private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
		return Math.abs(enemy.getTranslateX()) > screenWidth;
	}


	/**
	 * Ends the game with a win, displaying the victory screen.
	 *
	 * @param primaryStage The stage where the victory screen will be displayed.
	 */
	protected void winGame(Stage primaryStage) {
		timeline.stop();
		background.setOnKeyPressed(null); // Disable key actions
		levelView.showYouWonScreen(primaryStage);
		 	}


	/**
	 * Ends the game with a loss, displaying the game over screen.
	 *
	 * @param primaryStage The stage where the game over screen will be displayed.
	 */
	protected void loseGame(Stage primaryStage) {
		timeline.stop();
		background.setOnKeyPressed(null); // Disable key actions
		levelView.showGameOverScreen(primaryStage);
	}


	/**
	 * Gets the user plane object for the level.
	 *
	 * @return The user plane.
	 */
	public UserPlane getUser() {
		return user;
	}

	/**
	 * Gets the root group of the level, which holds all graphical elements.
	 *
	 * @return The root group.
	 */
	public Group getRoot() {
		return root;
	}

	/**
	 * Gets the current number of active enemy units in the level.
	 *
	 * @return The number of enemy units.
	 */
	protected int getCurrentNumberOfEnemies() {
		return enemyUnits.size();
	}

	/**
	 * Adds an enemy unit to the level and adds it to the root group.
	 *
	 * @param enemy The enemy unit to add.
	 */
	protected void addEnemyUnit(ActiveActorDestructible enemy) {
		enemyUnits.add(enemy);
		root.getChildren().add(enemy);
	}

	/**
	 * Gets the maximum Y position of enemy units in the level.
	 *
	 * @return The maximum Y position.
	 */
	protected double getEnemyMaximumYPosition() {
		return enemyMaximumYPosition;
	}

	/**
	 * Gets the width of the screen for the level.
	 *
	 * @return The screen width.
	 */
	protected double getScreenWidth() {
		return screenWidth;
	}

	/**
	 * Checks if the user plane has been destroyed.
	 *
	 * @return True if the user plane is destroyed, false otherwise.
	 */
	protected boolean userIsDestroyed() {
		return user.isDestroyed();
	}

	/**
	 * Updates the current number of enemy units.
	 */
	private void updateNumberOfEnemies() {
		currentNumberOfEnemies = enemyUnits.size();
	}

}
