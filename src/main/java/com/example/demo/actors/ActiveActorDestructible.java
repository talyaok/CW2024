package com.example.demo.actors;

import com.example.demo.Destructible;

/**
 * Represents a destructible active actor in the game.
 * This class extends {@link ActiveActor} and implements the {@link Destructible} interface,
 * allowing the actor to be damaged and destroyed during gameplay.
 * <p>
 * Subclasses must define specific behavior for updating the actor's position,
 * handling updates, and taking damage.
 * </p>
 *
 * @author Talya
 */
public abstract class ActiveActorDestructible extends ActiveActor implements Destructible {

	/**
	 * Indicates whether the actor is destroyed.
	 */
	private boolean isDestroyed;

	/**
	 * Creates an instance of a destructible active actor.
	 *
	 * @param imageName The name of the image file representing the actor.
	 * @param imageHeight The height of the actor's image in pixels. The width is scaled proportionally.
	 * @param initialXPos The initial x-coordinate of the actor.
	 * @param initialYPos The initial y-coordinate of the actor.
	 */
	public ActiveActorDestructible(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		isDestroyed = false;
	}

	/**
	 * Updates the position of the actor.
	 * This method must be implemented by subclasses to define specific movement behavior.
	 */
	@Override
	public abstract void updatePosition();

	/**
	 * Updates the actor's state.
	 * This method must be implemented by subclasses to handle specific actor updates.
	 */
	public abstract void updateActor();

	/**
	 * Handles the logic for taking damage.
	 * This method must be implemented by subclasses to define damage handling behavior.
	 */
	@Override
	public abstract void takeDamage();

	/**
	 * Marks the actor as destroyed.
	 * The {@code isDestroyed} flag is set to {@code true}.
	 */
	@Override
	public void destroy() {
		setDestroyed(true);
	}

	/**
	 * Sets the destroyed state of the actor.
	 *
	 * @param isDestroyed {@code true} if the actor is destroyed; {@code false} otherwise.
	 */
	protected void setDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}

	/**
	 * Checks whether the actor is destroyed.
	 *
	 * @return {@code true} if the actor is destroyed; {@code false} otherwise.
	 */
	public boolean isDestroyed() {
		return isDestroyed;
	}
	
}
