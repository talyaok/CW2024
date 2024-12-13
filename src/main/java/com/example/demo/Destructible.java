package com.example.demo;

/**
 * The Destructible interface defines the contract for objects that can take damage and be destroyed.
 * Classes implementing this interface should provide the logic for taking damage and destroying the object.
 *
 * This interface is typically used in game development, where game objects (e.g., enemies, obstacles) can be damaged
 * and destroyed during gameplay.
 *
 * @author Talya
 */
public interface Destructible {

	/**
	 * Method to handle the object taking damage. Implementations should define the behavior
	 * when the object is damaged (e.g., decrease health, apply damage effects).
	 */
	void takeDamage();

	/**
	 * Method to destroy the object. Implementations should define the behavior when the object is destroyed,
	 * such as removing it from the game, playing destruction effects, etc.
	 */
	void destroy();
	
}
