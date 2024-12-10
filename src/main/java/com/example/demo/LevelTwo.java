package com.example.demo;

import javafx.stage.Stage;

public class LevelTwo extends LevelParent {

	private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/bglevel2.png";
	private static final int PLAYER_INITIAL_HEALTH = 5;
	private final Boss boss;
	private LevelViewLevelTwo levelView;
	private final Stage primaryStage;

	public LevelTwo(double screenHeight, double screenWidth, Stage primaryStage) {
		super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH);
		this.primaryStage = primaryStage;
		boss = new Boss();
		System.out.println("LevelTwo initialized with user health: " + getUser().getHealth());
		//getUser().setHealth(PLAYER_INITIAL_HEALTH); // Ensure player's health is correctly set. comment out for testing
	}

	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getUser());
		System.out.println("Friendly units initialized in LevelTwo.");
		//getRoot().getChildren().add(boss.getShieldImage());//d
	}
	@Override protected void initializeContainers() {
		initializeBossLivesDisplay(boss);
	}

/*
	@Override
	protected void checkIfGameOver() {
		if (userIsDestroyed()) {
			loseGame(primaryStage);
		}
		else if (boss.isDestroyed()) {
			winGame();
		}
	}

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

	@Override
	protected void spawnEnemyUnits() {
		if (getCurrentNumberOfEnemies() == 0) {
			addEnemyUnit(boss);
			System.out.println("Boss added to the game.");
		}
	}

	@Override
	protected LevelView instantiateLevelView() {
		levelView = new LevelViewLevelTwo(getRoot(), PLAYER_INITIAL_HEALTH);
		//levelView.showShield();//d
		System.out.println("LevelViewLevelTwo instantiated.");
		return levelView;
	}

}
