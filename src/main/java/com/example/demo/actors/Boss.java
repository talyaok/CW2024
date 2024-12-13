package com.example.demo.actors;

import java.util.*;

/**
 * Represents the boss character in the game.
 * The Boss is a destructible actor with unique behaviors, including movement patterns,
 * firing projectiles, and activating a shield for protection.
 *
 * This class extends {@link FighterPlane} and implements specific functionalities,
 * such as firing projectiles, updating its position, and managing a shield.
 *
 * @author Talya
 */
public class Boss extends FighterPlane {

	// Constants
	private static final String IMAGE_NAME = "bossplane1.png";
	private static final double INITIAL_X_POSITION = 1000.0;
	private static final double INITIAL_Y_POSITION = 400;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 75.0;
	private static final double BOSS_FIRE_RATE = .04;
	private static final double BOSS_SHIELD_PROBABILITY = .002;
	private static final int IMAGE_HEIGHT = 100;
	private static final int VERTICAL_VELOCITY = 8;
	private static final int HEALTH = 10;//set to something else (was 100)
	private static final int MOVE_FREQUENCY_PER_CYCLE = 5;
	private static final int ZERO = 0;
	private static final int MAX_FRAMES_WITH_SAME_MOVE = 10;
	private static final int Y_POSITION_UPPER_BOUND = -100;
	private static final int Y_POSITION_LOWER_BOUND = 475;
	private static final int MAX_FRAMES_WITH_SHIELD = 50;

	// Fields
	private final List<Integer> movePattern;
	private boolean isShielded;
	private int consecutiveMovesInSameDirection;
	private int indexOfCurrentMove;
	private int framesWithShieldActivated;
	private ShieldImage shieldImage; //for shield(d)

	/**
	 * Constructs a Boss object with predefined attributes, including position, size,
	 * health, and a shield image.
	 */
	public Boss() {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH);
		movePattern = new ArrayList<>();
		consecutiveMovesInSameDirection = 0;
		indexOfCurrentMove = 0;
		framesWithShieldActivated = 0;
		isShielded = false;
		shieldImage = new ShieldImage(INITIAL_X_POSITION,INITIAL_Y_POSITION);//d
		getShieldImage();//d
		initializeMovePattern();
	}

	/**
	 * Retrieves the ShieldImage object associated with the Boss.
	 *
	 * @return The {@link ShieldImage} representing the Boss's shield.
	 */
	//adding getter method(d)
	public ShieldImage getShieldImage(){
		return shieldImage;
	}



	/**
	 * Updates the Boss's position based on its current movement pattern.
	 * Prevents the Boss from moving out of predefined boundaries.
	 */
	@Override
	public void updatePosition() {
		double initialTranslateY = getTranslateY();
		moveVertically(getNextMove());
		double currentPosition = getLayoutY() + getTranslateY();
		if (currentPosition < Y_POSITION_UPPER_BOUND || currentPosition > Y_POSITION_LOWER_BOUND) {
			setTranslateY(initialTranslateY);
		}

		if (isShielded) {//d
			shieldImage.updatePosition(getLayoutX(), getLayoutY()); // Update shield position(d)
			System.out.println("Shield Position Updated(BOSS class): " + getLayoutX() + ", " + getLayoutY());//d
		}//d


	}


	/*
	@Override
	public void updatePosition() {
	double initialTranslateY = getTranslateY();
	moveVertically(getNextMove());
	double currentPosition = getLayoutY() + getTranslateY();
	if (currentPosition < Y_POSITION_UPPER_BOUND || currentPosition > Y_POSITION_LOWER_BOUND) {
		setTranslateY(initialTranslateY);
	}
}

	 */

	/**
	 * Updates the Boss's state, including its position and shield.
	 */
	@Override
	public void updateActor() {
		updatePosition();
		updateShield();
	}

	/**
	 * Fires a projectile if the Boss is allowed to fire in the current frame.
	 *
	 * @return A new {@link BossProjectile} if firing is allowed; {@code null} otherwise.
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		return bossFiresInCurrentFrame() ? new BossProjectile(getProjectileInitialPosition()) : null;
	}

	/**
	 * Handles damage taken by the Boss.
	 * Damage is only applied if the shield is not active.
	 */
	@Override
	public void takeDamage() {
		if (!isShielded) {
            super.takeDamage();
        }
	}

	/**
	 * Initializes the movement pattern for the Boss, which includes vertical and stationary moves.
	 */
	private void initializeMovePattern() {
		for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
			movePattern.add(VERTICAL_VELOCITY);
			movePattern.add(-VERTICAL_VELOCITY);
			movePattern.add(ZERO);
		}
		Collections.shuffle(movePattern);
	}

	/**
	 * Updates the shield state, including activation and deactivation based on conditions.
	 */
	private void updateShield() {
		if (isShielded){ framesWithShieldActivated++;
		shieldImage.updatePosition(getLayoutX() + getTranslateX(),//d
				getLayoutY() + getTranslateY());}//d
		else if (shieldShouldBeActivated()) activateShield();	
		if (shieldExhausted()) deactivateShield();
	}

	

	/**
	 * Retrieves the next move from the movement pattern.
	 *
	 * @return The vertical movement value for the current frame.
	 */
	private int getNextMove() {
		int currentMove = movePattern.get(indexOfCurrentMove);
		consecutiveMovesInSameDirection++;
		if (consecutiveMovesInSameDirection == MAX_FRAMES_WITH_SAME_MOVE) {
			Collections.shuffle(movePattern);
			consecutiveMovesInSameDirection = 0;
			indexOfCurrentMove++;
		}
		if (indexOfCurrentMove == movePattern.size()) {
			indexOfCurrentMove = 0;
		}
		return currentMove;
	}

	/**
	 * Determines whether the Boss should fire a projectile in the current frame.
	 *
	 * @return {@code true} if the Boss fires; {@code false} otherwise.
	 */
	private boolean bossFiresInCurrentFrame() {
		return Math.random() < BOSS_FIRE_RATE;
	}

	/**
	 * Gets the initial y-position for a projectile fired by the Boss.
	 *
	 * @return The y-coordinate for the projectile's starting position.
	 */
	private double getProjectileInitialPosition() {
		return getLayoutY() + getTranslateY() + PROJECTILE_Y_POSITION_OFFSET;
	}

/*
	private boolean shieldShouldBeActivated() {
		return Math.random() < BOSS_SHIELD_PROBABILITY;
	}

 */

	/**
	 * Determines whether the shield should be activated based on predefined conditions.
	 *
	 * @return {@code true} if the shield should be activated; {@code false} otherwise.
	 */
	private boolean shieldShouldBeActivated() {
		return true;//fixed this was random
	}



	/**
	 * Determines whether the shield has been active for its maximum duration.
	 *
	 * @return {@code true} if the shield is exhausted; {@code false} otherwise.
	 */
	private boolean shieldExhausted() {
		return framesWithShieldActivated == MAX_FRAMES_WITH_SHIELD;
	}

	/**
	 * Activates the Boss's shield and displays the shield image.
	 */
	private void activateShield() {
		isShielded = true;
		shieldImage.showShield();//d
	}

	/**
	 * Deactivates the Boss's shield and hides the shield image.
	 */
	private void deactivateShield() {
		isShielded = false;
		framesWithShieldActivated = 0;
		shieldImage.hideShield();//d

	}

}
