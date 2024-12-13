package com.example.demo.actors;

import com.example.demo.actors.ActiveActorDestructible;

/**
 * Represents a projectile in the game. The projectile is a type of active actor that is capable of movement
 * and can be destroyed upon taking damage.
 *
 * This class provides a base for various types of projectiles (e.g., bullets, missiles) to inherit common functionality,
 * such as updating position and handling destruction upon damage.
 *
 * @author Talya
 */
public abstract class Projectile extends ActiveActorDestructible {

	/**
	 * Constructs a new Projectile with the specified properties.
	 *
	 * @param imageName The name of the image representing the projectile.
	 * @param imageHeight The height of the projectile's image.
	 * @param initialXPos The initial x-coordinate of the projectile.
	 * @param initialYPos The initial y-coordinate of the projectile.
	 */
	public Projectile(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
	}

	/**
	 * Handles the damage taken by the projectile. When a projectile takes damage, it is destroyed.
	 * This method overrides the takeDamage method from {@link ActiveActorDestructible}.
	 */
	@Override
	public void takeDamage() {
		this.destroy();
	}// Destroy the projectile upon taking damage.

	/**
	 * Abstract method to update the position of the projectile.
	 * This must be implemented by subclasses to define how the projectile moves.
	 */
	@Override
	public abstract void updatePosition();

}
