package com.example.demo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ShieldImage extends ImageView {
	
	private static final String IMAGE_NAME = "/images/shield.png";
	private static final int SHIELD_SIZE = 100;
	//private boolean isShielded = false;//d
	
	public ShieldImage(double xPosition, double yPosition) {
		this.setLayoutX(xPosition);
		this.setLayoutY(yPosition);
		//this.setImage(new Image(IMAGE_NAME));
		this.setImage(new Image(getClass().getResource("/com/example/demo/images/shield.png").toExternalForm()));//d jpg changed to png
		//this.setVisible(true);//was false now true d
		this.setVisible(false);
		this.setFitHeight(SHIELD_SIZE);
		this.setFitWidth(SHIELD_SIZE);
	}

	public void showShield() {
		this.setVisible(true);
		/*
		if(!isShielded){//d
			isShielded = true;//d
			this.setVisible(true);
			System.out.println("Shield on (shield image) " + getLayoutX() +"," + getLayoutY());//d
		}

		 */


	}
	
	public void hideShield() {
		this.setVisible(false);
		System.out.println("Shield off");//d
	}
/*
	// Method to update the shield position when moving the plane(d)
	public void updatePosition(double xPosition, double yPosition) {
		setLayoutX(xPosition);
		setLayoutY(yPosition);
		System.out.println("Shield Position Updated(Shield Image class): " + getLayoutX() + ", " + getLayoutY());

	}

 */
}
