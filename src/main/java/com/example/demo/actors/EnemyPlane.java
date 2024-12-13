package com.example.demo.actors;

/**
 * Represents an enemy plane in the game.
 * The EnemyPlane moves horizontally and can fire projectiles at a low rate.
 *
 * This class extends {@link FighterPlane} and implements its specific movement
 * and firing behavior.
 *
 * @author Talya
 */
public class EnemyPlane extends FighterPlane {

	// Constants
	private static final String IMAGE_NAME = "enemyplane1.png";
	private static final int IMAGE_HEIGHT = 30;
	private static final int HORIZONTAL_VELOCITY = -5;
	private static final double PROJECTILE_X_POSITION_OFFSET = -100.0;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 50.0;
	private static final int INITIAL_HEALTH = 1;
	private static final double FIRE_RATE = .01;

	/**
	 * Constructs an EnemyPlane at the specified position.
	 *
	 * @param initialXPos The initial x-coordinate of the EnemyPlane.
	 * @param initialYPos The initial y-coordinate of the EnemyPlane.
	 */
	public EnemyPlane(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
	}

	/**
	 * Updates the position of the EnemyPlane by moving it horizontally.
	 */
	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	/**
	 * Fires a projectile with a certain probability. The projectile is fired
	 * from the EnemyPlane's current position with specified offsets.
	 *
	 * @return An {@link EnemyProjectile} if firing occurs, or {@code null} otherwise.
	 */
	@Override
	public ActiveActorDestructible fireProjectile() {
		if (Math.random() < FIRE_RATE) {
			double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
			double projectileYPosition = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
			return new EnemyProjectile(projectileXPosition, projectileYPosition);
		}
		return null;
	}

	/**
	 * Updates the state of the EnemyPlane. This includes updating its position.
	 */
	@Override
	public void updateActor() {
		updatePosition();
	}

}
