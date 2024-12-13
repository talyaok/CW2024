package com.example.demo.actors;

/**
 * Represents a projectile fired by the Boss character in the game.
 * The BossProjectile moves horizontally at a constant velocity.
 *
 * This class extends {@link Projectile} and overrides methods to handle
 * its specific behavior and movement.
 *
 * @author Talya
 */
public class BossProjectile extends Projectile {

	// Constants
	private static final String IMAGE_NAME = "fireball.png";
	private static final int IMAGE_HEIGHT = 50;
	private static final int HORIZONTAL_VELOCITY = -15;
	private static final int INITIAL_X_POSITION = 950;

	/**
	 * Constructs a BossProjectile at a specified vertical position.
	 *
	 * @param initialYPos The initial y-coordinate of the projectile.
	 */
	public BossProjectile(double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, initialYPos);
	}

	/**
	 * Updates the position of the projectile by moving it horizontally.
	 * The movement is defined by a constant horizontal velocity.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Updates the state of the projectile. In this implementation,
	 * it simply updates its position.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}
	
}
