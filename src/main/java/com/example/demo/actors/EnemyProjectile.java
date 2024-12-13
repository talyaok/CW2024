package com.example.demo.actors;

/**
 * Represents a projectile fired by an enemy plane in the game.
 * The EnemyProjectile moves horizontally at a high velocity.
 *
 * This class extends {@link Projectile} and defines the behavior specific to
 * projectiles fired by enemy planes.
 *
 * @author Talya
 */
public class EnemyProjectile extends Projectile {

	// Constants

	private static final String IMAGE_NAME = "missile1.png";
	private static final int IMAGE_HEIGHT = 20;
	private static final int HORIZONTAL_VELOCITY = -20;

	/**
	 * Constructs an EnemyProjectile at the specified position.
	 *
	 * @param initialXPos The initial x-coordinate of the projectile.
	 * @param initialYPos The initial y-coordinate of the projectile.
	 */
	public EnemyProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
	}

	/**
	 * Updates the position of the projectile by moving it horizontally.
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
