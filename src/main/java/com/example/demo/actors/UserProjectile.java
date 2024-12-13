package com.example.demo.actors;

import com.example.demo.actors.Projectile;

/**
 * Represents a projectile fired by the user's plane in the game.
 * The `UserProjectile` class extends `Projectile` and defines the behavior for the user's fireball-like projectiles.
 *
 * The projectile moves horizontally and has a defined image and velocity.
 *
 * @author Talya
 */
public class UserProjectile extends Projectile {

	private static final String IMAGE_NAME = "userfire.png";
	private static final int IMAGE_HEIGHT = 15;
	private static final int HORIZONTAL_VELOCITY = 30;

	/**
	 * Constructs a new UserProjectile with the specified initial position.
	 *
	 * @param initialXPos The initial x-position of the projectile.
	 * @param initialYPos The initial y-position of the projectile.
	 */
	public UserProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
	}

	/**
	 * Updates the position of the projectile, moving it horizontally based on the defined velocity.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Updates the behavior of the projectile, including its position.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}
	
}
