package com.example.demo;

import java.util.ArrayList;
import java.util.List;

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
	//private int health;  // add health variable

	public UserPlane(int initialHealth) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, initialHealth);
		velocityMultiplier = 0;
		this.followerPlanes = new ArrayList<>(); // Initialize list
	}
	
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
	
	@Override
	public void updateActor() {
		updatePosition();
	}

	/*
	@Override
	public ActiveActorDestructible fireProjectile() {
		UserProjectile projectile = new UserProjectile(PROJECTILE_X_POSITION, getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET));
		return projectile;
		// changed dont know why or who??
		//return new UserProjectile(PROJECTILE_X_POSITION, getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET));
	}

	 */

	@Override
	public ActiveActorDestructible fireProjectile() {
		// Notify power-up planes to fire
		for (PowerUp powerUp : followerPlanes) {
			powerUp.fireProjectile();
		}
		return new UserProjectile(PROJECTILE_X_POSITION, getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET));
	}
	private boolean isMoving() {
		return velocityMultiplier != 0;
	}

	public void moveUp() {
		velocityMultiplier = -1;
	}

	public void moveDown() {
		velocityMultiplier = 1;
	}

	public void stop() {
		velocityMultiplier = 0;
	}

	public int getNumberOfKills() {
		return numberOfKills;
	}

	public void incrementKillCount() {
		numberOfKills++;
	}

	public void addFollowerPlane(PowerUp powerUp) {
		followerPlanes.add(powerUp);
	}
	private void updateFollowerPlanesPosition() {
		double userX = getLayoutX() + getTranslateX();
		double userY = getLayoutY() + getTranslateY();
		for (PowerUp powerUp : followerPlanes) {
			powerUp.followUser(userX, userY);
		}
	}
/*
	public int getHealth() { // Add getHealth method
		return health;
	}

 */
/* comment out for testing
	public void setHealth(int health) { // Add setHealth method
		this.health = health;
	}
 */
}


