package com.example.demo.actors;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the user's plane in the game, which is the player's character.
 * The plane can move vertically and fire projectiles. It also has a kill count
 * and can acquire follower planes from power-ups.
 *
 * The `UserPlane` class extends `FighterPlane` and manages the player's plane's
 * movement, projectiles, and follower planes.
 *
 * @author Talya
 */
public class UserPlane extends FighterPlane {

	private static final String IMAGE_NAME = "userplane1.png";
	private static final double Y_UPPER_BOUND = -40;
	private static final double Y_LOWER_BOUND = 600.0;
	private static final double INITIAL_X_POSITION = 5.0;
	private static final double INITIAL_Y_POSITION = 300.0;
	private static final int IMAGE_HEIGHT = 50;
	private static final int VERTICAL_VELOCITY = 8;
	private static final int PROJECTILE_X_POSITION = 110;
	private static final int PROJECTILE_Y_POSITION_OFFSET = 20;
	private int velocityMultiplier;
	private int numberOfKills;
	private final List<PowerUp> followerPlanes; // Add list of follower planes


	/**
	 * Constructs a new UserPlane with the specified initial health.
	 *
	 * @param initialHealth The initial health of the user plane.
	 */
	public UserPlane(int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		velocityMultiplier = 0;
		this.followerPlanes = new ArrayList<>(); // Initialize list
	}

	/**
	 * Updates the position of the user plane based on its current movement state.
	 * If the plane is moving, it will adjust its y-position within the defined bounds.
	 * It also updates the positions of any follower planes.
	 */
	@Override
	public void updatePosition() {
		if (isMoving()) {
			double initialTranslateY = getTranslateY();
			this.moveVertically(VERTICAL_VELOCITY * velocityMultiplier);
			double newPosition = getLayoutY() + getTranslateY();
			if (newPosition < Y_UPPER_BOUND || newPosition > Y_LOWER_BOUND) {
				this.setTranslateY(initialTranslateY);
			}
		}
		updateFollowerPlanesPosition(); // Update followers' positions
	}

	/**
	 * Updates the user plane's behavior, including its position.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}


	/**
	 * Fires a projectile from the user plane. This also notifies follower planes to fire their projectiles.
	 *
	 * @return The new `UserProjectile` object representing the fired projectile.
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		// Notify power-up planes to fire
		for (PowerUp powerUp : followerPlanes) {
			powerUp.fireProjectile();
		}
		return new UserProjectile(PROJECTILE_X_POSITION, getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET));
	}

	/**
	 * Checks whether the user plane is currently moving.
	 *
	 * @return true if the user plane is moving (i.e., velocity multiplier is non-zero), false otherwise.
	 */
	private boolean isMoving() {
		return velocityMultiplier != 0;
	}

	/**
	 * Moves the user plane upwards by changing its velocity multiplier.
	 */
	public void moveUp() {
		velocityMultiplier = -1;
	}

	/**
	 * Moves the user plane downwards by changing its velocity multiplier.
	 */
	public void moveDown() {
		velocityMultiplier = 1;
	}

	/**
	 * Stops the movement of the user plane by setting the velocity multiplier to zero.
	 */
	public void stop() {
		velocityMultiplier = 0;
	}

	/**
	 * Returns the number of kills made by the user plane.
	 *
	 * @return The kill count.
	 */
	public int getNumberOfKills() {
		return numberOfKills;
	}

	/**
	 * Increments the kill count of the user plane by one.
	 */
	public void incrementKillCount() {
		numberOfKills++;
	}

	/**
	 * Adds a follower plane (from a power-up) to the user plane's follower list.
	 *
	 * @param powerUp The power-up plane to add as a follower.
	 */
	public void addFollowerPlane(PowerUp powerUp) {
		followerPlanes.add(powerUp);
	}

	/**
	 * Updates the positions of all follower planes to follow the user plane's current position.
	 */
	private void updateFollowerPlanesPosition() {
		double userX = getLayoutX() + getTranslateX();
		double userY = getLayoutY() + getTranslateY();
		for (PowerUp powerUp : followerPlanes) {
			powerUp.followUser(userX, userY);
		}
	}
}


