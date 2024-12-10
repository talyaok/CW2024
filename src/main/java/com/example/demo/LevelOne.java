package com.example.demo;

import javafx.stage.Stage;

public class LevelOne extends LevelParent {
	
	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/bglevel1.jpg";
	private static final String NEXT_LEVEL = "com.example.demo.LevelTwo";
	private static final int TOTAL_ENEMIES = 10;
	private static final int KILLS_TO_ADVANCE = 10;
	private static final double ENEMY_SPAWN_PROBABILITY = .20;
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private boolean checkLevelTransition = false; //d

	private final Stage primaryStage;

	public LevelOne(double screenHeight, double screenWidth, Stage primaryStage) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
		this.primaryStage = primaryStage;

	}

	@Override
	protected void initializeContainers() {
		initializePlanesLeftDisplay(TOTAL_ENEMIES); //new
	}

	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame(primaryStage);
		}
		else if (userHasReachedKillTarget() && !checkLevelTransition){//d
			checkLevelTransition = true;//d
			System.out.println("level 2 starts");//d
			goToNextLevel(NEXT_LEVEL);//d code altered to check problems can be og??
			//showLevelOneCompleteScreen(primaryStage); // Show level complete screen
		}

	}

	@Override
	protected void initializeFriendlyUnits() {

		getRoot().getChildren().add(getUser());

	}


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

	@Override
	protected LevelView instantiateLevelView() {
		//LevelView levelView = new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
		//levelView.showHeartDisplay();
		//return levelView;
		return new LevelView(getRoot(), PLAYER_INITIAL_HEALTH);
	}

	private boolean userHasReachedKillTarget() {
		return getUser().getNumberOfKills() >= KILLS_TO_ADVANCE;
	}
/*
	private void showLevelOneCompleteScreen(Stage primaryStage) {
		try {
			LevelOneCompleteScreen levelOneCompleteScreen = new LevelOneCompleteScreen();
			levelOneCompleteScreen.start(primaryStage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

 */
}
