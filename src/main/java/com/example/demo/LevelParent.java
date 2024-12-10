package com.example.demo;

import java.util.*;
import java.util.stream.Collectors;

import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

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

	private final List<ActiveActorDestructible> friendlyUnits;
	private final List<ActiveActorDestructible> enemyUnits;
	private final List<ActiveActorDestructible> userProjectiles;
	private final List<ActiveActorDestructible> enemyProjectiles;
	
	private int currentNumberOfEnemies;
	private LevelView levelView;
	//private boolean heartDisplayShown = false; // Add this flag
	private PlanesLeftDisplay planesLeftDisplay; // Add PlanesLeftDisplay field
	private BossLivesDisplay bossLivesDisplay; // Add BossLivesDisplay field
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
	// Initialize planes left display
	protected void initializePlanesLeftDisplay(int totalEnemies) {
		planesLeftDisplay = new PlanesLeftDisplay(root, screenWidth, 25, user, totalEnemies);
		planesLeftDisplay.start();
	}
	// Initialize boss lives container
	protected void initializeBossLivesDisplay(Boss boss) {
		bossLivesDisplay = new BossLivesDisplay(root, screenWidth, 25, boss);
		bossLivesDisplay.start();
	}


	protected abstract void initializeFriendlyUnits();

	protected abstract void initializeContainers(); // Add this method to be overridden by subclasses

	protected abstract void checkIfGameOver();

	protected abstract void spawnEnemyUnits();

	protected abstract LevelView instantiateLevelView();

	public Scene initializeScene() {
		initializeBackground();
		initializeFriendlyUnits();
		initializeContainers(); // Initialize containers in subclasses
		levelView.showHeartDisplay();
		//initializePlanesLeftDisplay(10); // Initialize with total enemies count
		return scene;
	}


	/*
        public Scene initializeScene() {
            initializeBackground();
            initializeFriendlyUnits();
            if (!heartDisplayShown) { // Add this check
                levelView.showHeartDisplay();
                heartDisplayShown = true; // Set the flag to true
                } return scene; }
     */

	public void startGame() {
		background.requestFocus();
		timeline.play();
		System.out.println("Game started and timeline playing.");
		 	}


	public void goToNextLevel(String levelName) {
		System.out.println("Transitioning to next level: " + levelName);
		setChanged();
		notifyObservers(levelName);
	}

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
		//updatePlanesLeftText();
		checkIfGameOver();
	}

	private void initializeTimeline() {
		timeline.setCycleCount(Timeline.INDEFINITE);
		KeyFrame gameLoop = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> updateScene());
		timeline.getKeyFrames().add(gameLoop);
		System.out.println("Timeline initialized.");
	}

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
/*
	private void fireProjectile() {
		ActiveActorDestructible projectile = user.fireProjectile();
		if (projectile != null) { //t my code can be changed back?
			root.getChildren().add(projectile);
			userProjectiles.add(projectile);
		} else {//t
			System.out.println("No projectile created!");//t
		}
	}

 */
	private void fireProjectile() {
		ActiveActorDestructible projectile = user.fireProjectile();
		root.getChildren().add(projectile);
		userProjectiles.add(projectile);
	}

	private void generateEnemyFire() {
		enemyUnits.forEach(enemy -> spawnEnemyProjectile(((FighterPlane) enemy).fireProjectile()));
	}

	private void spawnEnemyProjectile(ActiveActorDestructible projectile) {
		if (projectile != null) {
			root.getChildren().add(projectile);
			enemyProjectiles.add(projectile);
			System.out.println("Enemy fired a projectile.");
		}
	}

	private void updateActors() {
		friendlyUnits.forEach(plane -> plane.updateActor());
		enemyUnits.forEach(enemy -> enemy.updateActor());
		userProjectiles.forEach(projectile -> projectile.updateActor());
		enemyProjectiles.forEach(projectile -> projectile.updateActor());
	}

	private void removeAllDestroyedActors() {
		removeDestroyedActors(friendlyUnits);
		removeDestroyedActors(enemyUnits);
		removeDestroyedActors(userProjectiles);
		removeDestroyedActors(enemyProjectiles);
	}

	private void removeDestroyedActors(List<ActiveActorDestructible> actors) {
		List<ActiveActorDestructible> destroyedActors = actors.stream().filter(actor -> actor.isDestroyed())
				.collect(Collectors.toList());
		root.getChildren().removeAll(destroyedActors);
		actors.removeAll(destroyedActors);
	}

	private void handlePlaneCollisions() {
		handleCollisions(friendlyUnits, enemyUnits);
	}

	private void handleUserProjectileCollisions() {
		handleCollisions(userProjectiles, enemyUnits);
	}

	private void handleEnemyProjectileCollisions() {
		handleCollisions(enemyProjectiles, friendlyUnits);
	}
/*
	private void handleCollisions(List<ActiveActorDestructible> actors1,
			List<ActiveActorDestructible> actors2) {
		for (ActiveActorDestructible actor : actors2) {
			for (ActiveActorDestructible otherActor : actors1) {
				if (actor.getBoundsInParent().intersects(otherActor.getBoundsInParent()))
					if (!actor.isDestroyed() && !otherActor.isDestroyed()) {//my code can be reverted?
						 System.out.println("collision detected between" + actor + "and" + otherActor);//t
						actor.takeDamage();
						otherActor.takeDamage();
					}
				}
			}
		}
	}

 */


	/*
	private void handleCollisions(List<ActiveActorDestructible> actors1,
								  List<ActiveActorDestructible> actors2) {
		for (ActiveActorDestructible actor : actors2) {
			for (ActiveActorDestructible otherActor : actors1) {
				if (actor.getBoundsInParent().intersects(otherActor.getBoundsInParent())) {
					actor.takeDamage();
					otherActor.takeDamage();
				}
			}
		}
	}

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
							//updatePlanesLeftText();
						}
						System.out.println("Collision detected between " + actor + " and " + otherActor); // Debug
					}
				}
			}
		}
	}




	private void handleEnemyPenetration() {
		for (ActiveActorDestructible enemy : enemyUnits) {
			if (enemyHasPenetratedDefenses(enemy)) {
				user.takeDamage();
				enemy.destroy();
			}
		}
	}

	private void updateLevelView() {
		levelView.removeHearts(user.getHealth());
	}

	private void updateKillCount() {
		for (int i = 0; i < currentNumberOfEnemies - enemyUnits.size(); i++) {
			user.incrementKillCount();
		}
	}

	private boolean enemyHasPenetratedDefenses(ActiveActorDestructible enemy) {
		return Math.abs(enemy.getTranslateX()) > screenWidth;
	}
/*
	private void updatePlanesLeftText() {
		int planesLeft = 5 - getUser().getNumberOfKills();
		planesLeftText.setText("Planes Left: " + planesLeft);
		planesLeftText.setX(getScreenWidth() - planesLeftText.getLayoutBounds().getWidth() - 10);
		System.out.println("Updated Planes Left text: " + planesLeftText.getText());
	}

 */

	protected void winGame(Stage primaryStage) {
		timeline.stop();
		background.setOnKeyPressed(null); // Disable key actions
		//levelView.showWinImage();
		//levelView.showYouWonScreen((Stage) scene.getWindow()); // Show You Won screen
		levelView.showYouWonScreen(primaryStage);
		 	}


/*
	protected void loseGame() {
		timeline.stop();
		levelView.showGameOverImage();
	}

 */


	protected void loseGame(Stage primaryStage) {
		timeline.stop();
		background.setOnKeyPressed(null); // Disable key actions
		levelView.showGameOverScreen(primaryStage);
	}


	protected UserPlane getUser() {
		return user;
	}

	protected Group getRoot() {
		return root;
	}

	protected int getCurrentNumberOfEnemies() {
		return enemyUnits.size();
	}

	protected void addEnemyUnit(ActiveActorDestructible enemy) {
		enemyUnits.add(enemy);
		root.getChildren().add(enemy);
	}

	protected double getEnemyMaximumYPosition() {
		return enemyMaximumYPosition;
	}

	protected double getScreenWidth() {
		return screenWidth;
	}

	protected boolean userIsDestroyed() {
		return user.isDestroyed();
	}

	private void updateNumberOfEnemies() {
		currentNumberOfEnemies = enemyUnits.size();
	}

}
