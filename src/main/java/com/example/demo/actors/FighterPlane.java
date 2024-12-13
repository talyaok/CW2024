package com.example.demo.actors;

import com.example.demo.actors.ActiveActorDestructible;

/**
 * Represents a fighter plane in the game, capable of firing projectiles and taking damage.
 * The FighterPlane class is a subclass of {@link ActiveActorDestructible} and
 * handles the plane's health, damage, and projectile firing behavior.
 *
 * This class serves as a base for more specific types of fighter planes.
 *
 * @author Talya
 */
public abstract class FighterPlane extends ActiveActorDestructible {

	// Instance variable for health
	private int health;

	/**
	 * Constructs a FighterPlane with a specified image, position, and health.
	 *
	 * @param imageName The name of the image representing the fighter plane.
	 * @param imageHeight The height of the fighter plane's image.
	 * @param initialXPos The initial x-coordinate of the fighter plane.
	 * @param initialYPos The initial y-coordinate of the fighter plane.
	 * @param health The initial health of the fighter plane.
	 */
	public FighterPlane(String imageName, int imageHeight, double initialXPos, double initialYPos, int health) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		this.health = health;
	}

	/**
	 * Fires a projectile from the fighter plane.
	 * The specific implementation of firing a projectile is determined by the subclass.
	 *
	 * @return An instance of {@link ActiveActorDestructible} representing the fired projectile.
	 */
	public abstract ActiveActorDestructible fireProjectile();

	/**
	 * Reduces the fighter plane's health by 1 when it takes damage.
	 * If the health reaches zero, the fighter plane is destroyed.
	 */
	@Override
	public void takeDamage() {
		health--;
		if (healthAtZero()) {
			this.destroy();
		}
	}

	/**
	 * Calculates the x-coordinate of the projectile based on the fighter plane's position
	 * and a specified offset.
	 *
	 * @param xPositionOffset The offset to be added to the fighter plane's x-coordinate.
	 * @return The x-coordinate of the projectile.
	 */
	protected double getProjectileXPosition(double xPositionOffset) {
		return getLayoutX() + getTranslateX() + xPositionOffset;
	}

	/**
	 * Calculates the y-coordinate of the projectile based on the fighter plane's position
	 * and a specified offset.
	 *
	 * @param yPositionOffset The offset to be added to the fighter plane's y-coordinate.
	 * @return The y-coordinate of the projectile.
	 */
	protected double getProjectileYPosition(double yPositionOffset) {
		return getLayoutY() + getTranslateY() + yPositionOffset;
	}

	/**
	 * Checks if the fighter plane's health has reached zero.
	 *
	 * @return {@code true} if the health is zero, {@code false} otherwise.
	 */
	private boolean healthAtZero() {
		return health == 0;
	}

	/**
	 * Gets the current health of the fighter plane.
	 *
	 * @return The current health of the fighter plane.
	 */
	public int getHealth() {
		return health;
	}
		
}
