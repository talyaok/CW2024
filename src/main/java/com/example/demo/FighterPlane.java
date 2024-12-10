package com.example.demo;

public abstract class FighterPlane extends ActiveActorDestructible {

	private int health;

	public FighterPlane(String imageName, int imageHeight, double initialXPos, double initialYPos, int health) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		this.health = health;
	}

	public abstract ActiveActorDestructible fireProjectile();
/*
	//this method i changed for testing can be changed back I think??
	@Override
	public void takeDamage() {
		if (!isDestroyed()) {
		health--;
		System.out.println("Health decreased! Current health:" + health);
		if (health <= 0) {
			destroy();
		}
		}
	}

 */

	@Override
	public void takeDamage() {
		health--;
		if (healthAtZero()) {
			this.destroy();
		}
	}
	protected double getProjectileXPosition(double xPositionOffset) {
		return getLayoutX() + getTranslateX() + xPositionOffset;
	}

	protected double getProjectileYPosition(double yPositionOffset) {
		return getLayoutY() + getTranslateY() + yPositionOffset;
	}

	private boolean healthAtZero() {
		return health == 0;
	}

	public int getHealth() {
		return health;
	}
		
}
